package com.runescape.util;

import java.applet.Applet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class Signlink implements Runnable {

	public static int uid;
	public static int storeId = 32;
	public static RandomAccessFile cacheDat = null;
	public static RandomAccessFile[] cacheIdx = new RandomAccessFile[5];
	public static Applet applet = null;
	private static boolean active;
	private static InetAddress inetAddress;
	private static int socketRequest;
	private static Socket socket = null;
	private static int threadRequestPriority = 1;
	private static Runnable threadRequest = null;
	private static String dnsRequest = null;
	public static String dns = null;
	private static String urlRequest = null;
	private static DataInputStream nextURLStream = null;
	private static int saveLength;
	private static String saveRequest = null;
	private static byte[] saveBuffer = null;
	private static boolean midiPlay;
	private static int midiPosition;
	public static String midi = null;
	public static int midiVolume;
	public static int midiFade;
	private static boolean wavePlay;
	private static int wavePosition;
	public static String wave = null;
	public static int waveVolume;
	public static boolean reportError = true;
	public static String errorName = "";

	public static final void initialize(InetAddress inetAddress) {
		if (Signlink.active) {
			try {
				Thread.sleep(500L);
			} catch (Exception exception) {
				/* empty */
			}
			Signlink.active = false;
		}
		Signlink.socketRequest = 0;
		Signlink.threadRequest = null;
		Signlink.dnsRequest = null;
		Signlink.saveRequest = null;
		Signlink.urlRequest = null;
		Signlink.inetAddress = inetAddress;
		Thread thread = new Thread(new Signlink());
		thread.setDaemon(true);
		thread.start();
		while (!Signlink.active) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	@Override
	public final void run() {
		active = true;
		String directory = getCacheDirectory();
		uid = getUID(getRootDirectory());

		try {
			File file = new File(directory + "main_file_cache.dat");
			if (file.exists() && file.length() > 0x3200000) {
				file.delete();
			}
			Signlink.cacheDat = new RandomAccessFile(directory + "main_file_cache.dat", "rw");
			for (int idx = 0; idx < 5; idx++) {
				Signlink.cacheIdx[idx] = new RandomAccessFile(directory + "main_file_cache.idx" + idx, "rw");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		while (true) {
			if (Signlink.socketRequest != 0) {
				try {
					Signlink.socket = new Socket(Signlink.inetAddress, Signlink.socketRequest);
				} catch (Exception exception) {
					Signlink.socket = null;
				}
				Signlink.socketRequest = 0;
			} else if (Signlink.threadRequest != null) {
				Thread thread = new Thread(Signlink.threadRequest);
				thread.setDaemon(true);
				thread.start();
				thread.setPriority(Signlink.threadRequestPriority);
				Signlink.threadRequest = null;
			} else if (Signlink.dnsRequest != null) {
				try {
					Signlink.dns = InetAddress.getByName(Signlink.dnsRequest).getHostName();
				} catch (Exception exception) {
					Signlink.dns = "unknown";
				}
				Signlink.dnsRequest = null;
			} else if (Signlink.saveRequest != null) {
				if (Signlink.saveBuffer != null) {
					try {
						FileOutputStream out = new FileOutputStream(directory + Signlink.saveRequest);
						out.write(Signlink.saveBuffer, 0, Signlink.saveLength);
						out.close();
					} catch (Exception exception) {
						/* empty */
					}
				}
				if (Signlink.wavePlay) {
					Signlink.wave = directory + Signlink.saveRequest;
					Signlink.wavePlay = false;
				}
				if (Signlink.midiPlay) {
					Signlink.midi = directory + Signlink.saveRequest;
					Signlink.midiPlay = false;
				}
				Signlink.saveRequest = null;
			} else if (Signlink.urlRequest != null) {
				try {
					Signlink.nextURLStream = new DataInputStream(new URL(Signlink.applet.getCodeBase(), Signlink.urlRequest).openStream());
				} catch (Exception exception) {
					Signlink.nextURLStream = null;
				}
				Signlink.urlRequest = null;
			}
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
	}

	private static String buildRoot() {
		StringBuilder bldr = new StringBuilder();
		bldr.append(System.getProperty("user.home"));
		bldr.append(File.separator);
		bldr.append("apollo.fs_").append(storeId);
		return bldr.toString();
	}

	private static final File ROOT = getAndCreateDir(buildRoot());

	private static File getAndCreateDir(String dir) {
		File file = new File(dir, File.separator);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static String getCacheDirectory() {
		File file = getAndCreateDir(getRootDirectory() + "cache");
		return file.getPath();
	}

	public static String getRootDirectory() {
		return ROOT.getPath();
	}

	private static int getUID(String directory) {
		try {
			File file = new File(directory + "random.dat");
			if (!file.exists() || file.length() < 4L) {
				DataOutputStream out = new DataOutputStream(new FileOutputStream(directory + "random.dat"));
				out.writeInt((int) (Math.random() * 9.9999999E7));
				out.close();
			}
		} catch (Exception exception) {
			/* empty */
		}
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(directory + "random.dat"));
			int i = in.readInt();
			in.close();
			return i + 1;
		} catch (Exception exception) {
			return 0;
		}
	}

	public static final synchronized Socket openSocket(int port) throws IOException {
		Signlink.socketRequest = port;
		while (Signlink.socketRequest != 0) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		if (Signlink.socket == null) {
			throw new IOException("could not open socket");
		}
		return Signlink.socket;
	}

	public static final synchronized DataInputStream openURL(String url) throws IOException {
		Signlink.urlRequest = url;
		while (Signlink.urlRequest != null) {
			try {
				Thread.sleep(50L);
			} catch (Exception exception) {
				/* empty */
			}
		}
		if (Signlink.nextURLStream == null) {
			throw new IOException("could not open: " + url);
		}
		return Signlink.nextURLStream;
	}

	public static final synchronized void dnsLookup(String dns) {
		Signlink.dns = dns;
		Signlink.dnsRequest = dns;
	}

	public static final synchronized void startRunnable(Runnable runnable, int priority) {
		Signlink.threadRequestPriority = priority;
		Signlink.threadRequest = runnable;
	}

	public static final synchronized boolean waveSave(byte[] buffer, int length) {
		if (length > 0x1E8480) {
			return false;
		}
		if (Signlink.saveRequest != null) {
			return false;
		}
		Signlink.wavePosition = (Signlink.wavePosition + 1) % 5;
		Signlink.saveLength = length;
		Signlink.saveBuffer = buffer;
		Signlink.wavePlay = true;
		Signlink.saveRequest = "sound" + Signlink.wavePosition + ".wav";
		return true;
	}

	public static final synchronized boolean waveReplay() {
		if (Signlink.saveRequest != null) {
			return false;
		}
		Signlink.saveBuffer = null;
		Signlink.wavePlay = true;
		Signlink.saveRequest = "sound" + Signlink.wavePosition + ".wav";
		return true;
	}

	public static final synchronized void midiSave(byte[] buffer, int length) {
		if (length <= 0x1E8480 && Signlink.saveRequest == null) {
			Signlink.midiPosition = (Signlink.midiPosition + 1) % 5;
			Signlink.saveLength = length;
			Signlink.saveBuffer = buffer;
			Signlink.midiPlay = true;
			Signlink.saveRequest = "jingle" + Signlink.midiPosition + ".mid";
		}
	}

	public static final void reportError(String error) {
		if (Signlink.reportError && Signlink.active) {
			System.out.println("Error: " + error);
			try {
				error = error.replace(':', '_');
				error = error.replace('@', '_');
				error = error.replace('&', '_');
				error = error.replace('#', '_');
				DataInputStream in = Signlink.openURL("reporterror" + 317 + ".cgi?error=" + Signlink.errorName + " " + error);
				in.readUTF();
				in.close();
			} catch (IOException ioexception) {
				/* empty */
			}
		}
	}
}
