package com.runescape.sound;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;

public final class TrackPlayer implements Runnable {

	private AudioInputStream audioStream;
	private Info info;
	private Clip sound;

	private InputStream inputStream;
	private Thread thread;
	private int delay;
	private int level;
	public static int volume;

	public TrackPlayer(InputStream inputStream, int level, int delay) {
		if (level == 0 || volume == 4 || level - volume <= 0) {
			return;
		}
		this.inputStream = inputStream;
		this.level = level;
		this.delay = delay;
		thread = new Thread(this);
	}

	public void start() {
		thread.start();
	}

	@Override
	public void run() {
		try {
			audioStream = AudioSystem.getAudioInputStream(inputStream);
			info = new DataLine.Info(Clip.class, audioStream.getFormat());
			sound = (Clip) AudioSystem.getLine(info);
			sound.open(audioStream);
			FloatControl volume = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(getDecibels(level - getVolume()));
			if (delay > 0) {
				Thread.sleep(delay);
			}
			sound.start();
			while (sound.isActive()) {
				Thread.sleep(250);
			}
			Thread.sleep(10000);
			sound.close();
			audioStream.close();
			thread.interrupt();
		} catch (Exception e) {
			thread.interrupt();
			e.printStackTrace();
		}
	}

	public static void setVolume(int level) {
		volume = level;
	}

	public static int getVolume() {
		return volume;
	}

	public float getDecibels(int level) {
		switch (level) {
		case 1:
			return -80.0F;
		case 2:
			return -70.0F;
		case 3:
			return -60.0F;
		case 4:
			return -50.0F;
		case 5:
			return -40.0F;
		case 6:
			return -30.0F;
		case 7:
			return -20.0F;
		case 8:
			return -10.0F;
		case 9:
			return -0.0F;
		case 10:
			return 6.0F;
		default:
			return 0.0F;
		}
	}

}