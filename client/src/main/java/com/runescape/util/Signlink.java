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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	public static Sequencer music = null;
	public static Sequence sequence = null;
	public static Synthesizer synthesizer = null;
	private Position curPosition;

	private enum Position {
		LEFT, RIGHT, NORMAL
	}

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
		try {
			active = true;
			String directory = getCacheDirectory();
			uid = getUID(getRootDirectory());

			File file = new File(directory + "main_file_cache.dat");
			if (file.exists() && file.length() > 0x3200000) {
				file.delete();
			}
			Signlink.cacheDat = new RandomAccessFile(directory + "main_file_cache.dat", "rw");
			for (int idx = 0; idx < 5; idx++) {
				Signlink.cacheIdx[idx] = new RandomAccessFile(directory + "main_file_cache.idx" + idx, "rw");
			}

			while (true) {
				if (Signlink.socketRequest != 0) {
					Signlink.socket = new Socket(Signlink.inetAddress, Signlink.socketRequest);
					Signlink.socketRequest = 0;
				} else if (Signlink.threadRequest != null) {
					Thread thread = new Thread(Signlink.threadRequest);
					thread.setDaemon(true);
					thread.start();
					thread.setPriority(Signlink.threadRequestPriority);
					Signlink.threadRequest = null;
				} else if (Signlink.dnsRequest != null) {
					Signlink.dns = InetAddress.getByName(Signlink.dnsRequest).getHostName();
					Signlink.dnsRequest = null;
				} else if (Signlink.saveRequest != null) {
					if (Signlink.saveBuffer != null) {
						FileOutputStream out = new FileOutputStream(directory + Signlink.saveRequest);
						out.write(Signlink.saveBuffer, 0, Signlink.saveLength);
						out.close();
					}
					if (Signlink.wavePlay) {
						String wave = directory + saveRequest;
						wavePlay = false;
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(wave));
						AudioFormat format = audioInputStream.getFormat();
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
						try (SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(info)) {
							auline.open(format);
							if (auline.isControlSupported(FloatControl.Type.PAN)) {
								FloatControl pan = (FloatControl) auline.getControl(FloatControl.Type.PAN);
								if (curPosition == Position.RIGHT) {
									pan.setValue(1.0f);
								} else if (curPosition == Position.LEFT) {
									pan.setValue(-1.0f);
								}
							}
							auline.start();
							byte[] data = new byte[524288];
							for (int read = audioInputStream.read(data, 0, data.length); read > -1;) {
								if (read >= 0) {
									auline.write(data, 0, read);
								}
							}
						}
					}
					if (midiPlay) {
						midi = directory + Signlink.saveRequest;
						if (music != null) {
							music.stop();
							music.close();
						}
						playMidi(midi);
						midiPlay = false;
					}
					Signlink.saveRequest = null;
				} else if (Signlink.urlRequest != null) {
					Signlink.nextURLStream = new DataInputStream(new URL(Signlink.applet.getCodeBase(), Signlink.urlRequest).openStream());
					Signlink.urlRequest = null;
				}
				Thread.sleep(50L);
			}

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InvalidMidiDataException | MidiUnavailableException | InterruptedException e) {
			e.printStackTrace();
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

	private void playMidi(String location) throws InvalidMidiDataException, IOException, MidiUnavailableException {
		File file = new File(location);
		sequence = MidiSystem.getSequence(file);
		music = MidiSystem.getSequencer();
		music.open();
		music.setSequence(sequence);
		synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();
		if (synthesizer.getDefaultSoundbank() == null) {
			music.getTransmitter().setReceiver(MidiSystem.getReceiver());
		} else {
			music.getTransmitter().setReceiver(synthesizer.getReceiver());
		}
		music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		music.start();
	}

	public static void setVolume(int midiVolume) throws InvalidMidiDataException, MidiUnavailableException {
		Signlink.midiVolume = midiVolume;

		if (synthesizer.getDefaultSoundbank() != null) {
			MidiChannel[] channels = synthesizer.getChannels();
			for (int channel = 0; channels != null && channel < channels.length; channel++) {
				channels[channel].controlChange(7, midiVolume);
				channels[channel].controlChange(39, midiVolume);
			}
		} else {
			ShortMessage volumeMessage = new ShortMessage();
			for (int channel = 0; channel < 16; channel++) {
				volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, channel, 7, midiVolume);
				volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, channel, 39, midiVolume);
				MidiSystem.getReceiver().send(volumeMessage, -1);
			}
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
