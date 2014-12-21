package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.Animation;
import com.runescape.net.Buffer;

public class AnimationSequence {

	public static int count;
	public static AnimationSequence[] cache;

	public int frameCount;
	public int[] primaryFrames;
	public int[] secondaryFrames;
	private int[] durations;
	public int loopOffset = -1;
	public int[] interleaveOrder;
	public boolean stretches = false;
	public int priority = 5;
	public int playerShieldDelta = -1;
	public int playerWeaponDelta = -1;
	public int maximumLoops = 99;
	public int animatingPrecedence = -1;
	public int walkingPrecedence = -1;
	public int replayMode = 2;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("seq.dat"));
		AnimationSequence.count = buffer.getUnsignedLEShort();
		if (AnimationSequence.cache == null) {
			AnimationSequence.cache = new AnimationSequence[AnimationSequence.count];
		}
		for (int animation = 0; animation < AnimationSequence.count; animation++) {
			if (AnimationSequence.cache[animation] == null) {
				AnimationSequence.cache[animation] = new AnimationSequence();
			}
			AnimationSequence.cache[animation].loadDefinition(buffer);
		}
	}

	public int getDuration(int animationId) {
		int duration = durations[animationId];
		if (duration == 0) {
			Animation animation = Animation.getAnimation(primaryFrames[animationId]);
			if (animation != null) {
				duration = durations[animationId] = animation.duration;
			}
		}

		return duration == 0 ? 1 : duration;
	}

	public void loadDefinition(Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				frameCount = buffer.getUnsignedByte();
				primaryFrames = new int[frameCount];
				secondaryFrames = new int[frameCount];
				durations = new int[frameCount];
				for (int frame = 0; frame < frameCount; frame++) {
					primaryFrames[frame] = buffer.getUnsignedLEShort();
					secondaryFrames[frame] = buffer.getUnsignedLEShort();
					if (secondaryFrames[frame] == 65535) {
						secondaryFrames[frame] = -1;
					}
					durations[frame] = buffer.getUnsignedLEShort();
				}
			} else if (attributeId == 2) {
				loopOffset = buffer.getUnsignedLEShort();
			} else if (attributeId == 3) {
				int count = buffer.getUnsignedByte();
				interleaveOrder = new int[count + 1];
				for (int flow = 0; flow < count; flow++) {
					interleaveOrder[flow] = buffer.getUnsignedByte();
				}
				interleaveOrder[count] = 9999999;
			} else if (attributeId == 4) {
				stretches = true;
			} else if (attributeId == 5) {
				priority = buffer.getUnsignedByte();
			} else if (attributeId == 6) {
				playerShieldDelta = buffer.getUnsignedLEShort();
			} else if (attributeId == 7) {
				playerWeaponDelta = buffer.getUnsignedLEShort();
			} else if (attributeId == 8) {
				maximumLoops = buffer.getUnsignedByte();
			} else if (attributeId == 9) {
				animatingPrecedence = buffer.getUnsignedByte();
			} else if (attributeId == 10) {
				walkingPrecedence = buffer.getUnsignedByte();
			} else if (attributeId == 11) {
				replayMode = buffer.getUnsignedByte();
			} else if (attributeId == 12) {
				buffer.getInt();
			} else {
				System.out.println("Error unrecognised seq config code: " + attributeId);
			}
		}

		if (frameCount == 0) {
			frameCount = 1;
			primaryFrames = new int[1];
			primaryFrames[0] = -1;
			secondaryFrames = new int[1];
			secondaryFrames[0] = -1;
			durations = new int[1];
			durations[0] = -1;
		}

		if (animatingPrecedence == -1) {
			if (interleaveOrder != null) {
				animatingPrecedence = 2;
			} else {
				animatingPrecedence = 0;
			}
		}

		if (walkingPrecedence != -1) {
			return;
		}

		if (interleaveOrder != null) {
			walkingPrecedence = 2;
		} else {
			walkingPrecedence = 0;
		}
	}

}