package org.apollo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.apollo.fs.FileSystem;

/**
 * A static-utility class containing containing extension or helper methods for
 * <b>co</b>mpressor-<b>dec</b>compressor<b>'s</b>.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class CompressionUtil {

	/**
	 * The default allocation length, equivalent to <tt>1</tt> megabyte or
	 * <tt>1024</tt> {@code byte}s.
	 */
	private static final int DEFAULT_ALLOCATION_LENGTH = 1024;

	/**
	 * Uncompresses a {@code byte} array of g-zipped data.
	 *
	 * @param data The compressed, g-zipped data.
	 * @return The uncompressed data.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static byte[] ungzip(byte[] data) throws IOException {
		return uncompress(new GZIPInputStream(new ByteArrayInputStream(data)));
	}

	/**
	 * Uncompresses a {@code byte} array of b-zipped data that does not contain
	 * a header.
	 *
	 * <p>
	 * A b-zip header block consists of <tt>2</tt> {@code byte}s, they are
	 * replaced with 'h' and '1' as that is what our {@link FileSystem file
	 * system} compresses the header as.
	 * </p>
	 *
	 * @param data The compressed, b-zipped data.
	 * @param offset The offset position of the data.
	 * @param length The length of the data.
	 * @return The uncompressed data.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static byte[] unbzip2Headerless(byte[] data, int offset, int length) throws IOException {
		/* Strip the header from the data. */
		byte[] bzip2 = new byte[length + 2];
		bzip2[0] = 'h';
		bzip2[1] = '1';
		System.arraycopy(data, offset, bzip2, 2, length);

		/* Uncompress the headerless data */
		return unbzip2(bzip2, offset, length);
	}

	/**
	 * Uncompresses a {@code byte} array of b-zipped data.
	 *
	 * @param data The compressed, b-zipped data.
	 * @param offset The offset position of the data.
	 * @param length The length of the data.
	 * @return The uncompressed data.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static byte[] unbzip2(byte[] data, int offset, int length) throws IOException {
		return uncompress(new CBZip2InputStream(new ByteArrayInputStream(data)));
	}

	/**
	 * Uncompresses a {@code byte} array from the specified {@link InputStream}
	 * with a fixed data length of {@link #DEFAULT_ALLOCATION_LENGTH}.
	 *
	 * @param is The input stream to uncompress the data from.
	 * @return The uncompressed data.
	 * @throws IOException If some I/O exception occurs.
	 */
	private static byte[] uncompress(InputStream is) throws IOException {
		return uncompress(is, DEFAULT_ALLOCATION_LENGTH);
	}

	/**
	 * Uncompresses a {@code byte} array from the specified {@link InputStream}.
	 *
	 * @param is The input stream to uncompress the data from.
	 * @param length The length of the data to uncompress.
	 * @return The uncompressed data.
	 * @throws IOException If some I/O exception occurs.
	 */
	private static byte[] uncompress(InputStream is, int length) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			byte[] buf = new byte[length];
			for (int len; (len = is.read(buf, 0, buf.length)) != -1;) {
				os.write(buf, 0, len);
			}
			return os.toByteArray();
		} finally {
			is.close();
		}
	}

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private CompressionUtil() {
		throw new UnsupportedOperationException("static-utility classes may not be instantiated.");
	}

}