package org.apollo.update;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import org.apollo.fs.FileSystem;
import org.apollo.update.resource.CombinedResourceProvider;
import org.apollo.update.resource.HypertextResourceProvider;
import org.apollo.update.resource.ResourceProvider;
import org.apollo.update.resource.VirtualResourceProvider;

import com.google.common.base.Charsets;

/**
 * A worker which services HTTP requests.
 *
 * @author Graham
 */
public final class HttpRequestWorker extends RequestWorker<HttpRequest, ResourceProvider> {

	/**
	 * The value of the server header.
	 */
	private static final String SERVER_IDENTIFIER = "JAGeX/3.1";

	/**
	 * The directory with web files.
	 */
	private static final Path WWW_PATH = Paths.get("data/www");

	/**
	 * The default character set.
	 */
	private static final Charset CHARACTER_SET = Charsets.ISO_8859_1;

	/**
	 * Creates the HTTP request worker.
	 *
	 * @param dispatcher The dispatcher.
	 * @param fs The file system.
	 */
	public HttpRequestWorker(UpdateDispatcher dispatcher, FileSystem fs) {
		super(dispatcher, new CombinedResourceProvider(new VirtualResourceProvider(fs), new HypertextResourceProvider(WWW_PATH)));
	}

	@Override
	protected ChannelRequest<HttpRequest> nextRequest(UpdateDispatcher dispatcher) throws InterruptedException {
		return dispatcher.nextHttpRequest();
	}

	@Override
	protected void service(ResourceProvider provider, Channel channel, HttpRequest request) throws IOException {
		String path = request.getUri();
		Optional<ByteBuffer> buf = provider.get(path);

		HttpResponseStatus status = HttpResponseStatus.OK;
		String mimeType = getMimeType(request.getUri());

		if (!buf.isPresent()) {
			status = HttpResponseStatus.NOT_FOUND;
			mimeType = "text/html";
		}

		ByteBuf wrappedBuf = buf.isPresent() ? Unpooled.wrappedBuffer(buf.get()) : createErrorPage(status, "The page you requested could not be found.");
		HttpResponse resp = new DefaultHttpResponse(request.getProtocolVersion(), status);

		resp.headers().set("Date", new Date());
		resp.headers().set("Server", SERVER_IDENTIFIER);
		resp.headers().set("Content-type", mimeType + ", charset=" + CHARACTER_SET.name());
		resp.headers().set("Cache-control", "no-cache");
		resp.headers().set("Pragma", "no-cache");
		resp.headers().set("Expires", new Date(0));
		resp.headers().set("Connection", "close");
		resp.headers().set("Content-length", wrappedBuf.readableBytes());

		channel.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Gets the MIME type of a file by its name.
	 *
	 * @param name The file name.
	 * @return The MIME type.
	 */
	private static String getMimeType(String name) {
		if (name.endsWith("/")) {
			name = name.concat("index.html");
		}

		if (name.endsWith(".htm") || name.endsWith(".html")) {
			return "text/html";
		} else if (name.endsWith(".css")) {
			return "text/css";
		} else if (name.endsWith(".js")) {
			return "text/javascript";
		} else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (name.endsWith(".gif")) {
			return "image/gif";
		} else if (name.endsWith(".png")) {
			return "image/png";
		} else if (name.endsWith(".txt")) {
			return "text/plain";
		}

		return "application/octect-stream";
	}

	/**
	 * Creates an error page.
	 *
	 * @param status The HTTP status.
	 * @param description The error description.
	 * @return The error page as a buffer.
	 */
	private static ByteBuf createErrorPage(HttpResponseStatus status, String description) {
		String title = status.code() + " " + status.reasonPhrase();

		return Unpooled.copiedBuffer("<!DOCTYPE html><html><head><title>" + title + "</title></head><body><h1>" + title + "</h1><p>" + description + "</p><hr /><address>" + SERVER_IDENTIFIER + " Server</address></body></html>", Charset.defaultCharset());
	}

}