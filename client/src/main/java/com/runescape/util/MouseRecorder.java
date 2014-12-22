package com.runescape.util;

import com.runescape.Game;

public class MouseRecorder implements Runnable {

	public Game client;
	public Object objectLock = new Object();
	public int[] coordsY = new int[500];
	public boolean capturing = true;
	public int[] coordsX = new int[500];
	public int recordedCoordinates;

	@Override
	public void run() {
		while (capturing) {
			synchronized (objectLock) {
				if (recordedCoordinates < 500) {
					coordsX[recordedCoordinates] = client.mouseEventX;
					coordsY[recordedCoordinates] = client.mouseEventY;
					recordedCoordinates++;
				}
			}
			try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public MouseRecorder(Game client) {
		this.client = client;
	}
}
