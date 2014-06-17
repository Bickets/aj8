package com.runescape.sound;

import com.runescape.net.Buffer;

public class Track {

    private static Track[] tracks = new Track[5000];
    public static int[] trackDelays = new int[5000];
    private static byte[] _buffer;
    private static Buffer buffer;
    private final Instrument[] instruments = new Instrument[10];
    private int loopBegin;
    private int loopEnd;

    public static final void load(Buffer buffer) {
	Track._buffer = new byte[441000];
	Track.buffer = new Buffer(Track._buffer);
	Instrument.initialize();
	while (true) {
	    int trackId = buffer.getUnsignedLEShort();
	    if (trackId == 65535) {
		break;
	    }
	    Track.tracks[trackId] = new Track();
	    Track.tracks[trackId].decode(buffer);
	    Track.trackDelays[trackId] = Track.tracks[trackId].delay();
	}
    }

    public static final Buffer data(int trackId, int loops) {
	if (Track.tracks[trackId] != null) {
	    Track soundtrack = Track.tracks[trackId];
	    return soundtrack.encode(loops);
	}
	return null;
    }

    private final void decode(Buffer buffer) {
	for (int instrument = 0; instrument < 10; instrument++) {
	    int active = buffer.getUnsignedByte();
	    if (active != 0) {
		buffer.offset--;
		instruments[instrument] = new Instrument();
		instruments[instrument].decode(buffer);
	    }
	}
	loopBegin = buffer.getUnsignedLEShort();
	loopEnd = buffer.getUnsignedLEShort();
    }

    private final int delay() {
	int delay = 9999999;
	for (int instrument = 0; instrument < 10; instrument++) {
	    if (instruments[instrument] != null && instruments[instrument].begin / 20 < delay) {
		delay = instruments[instrument].begin / 20;
	    }
	}
	if (loopBegin < loopEnd && loopBegin / 20 < delay) {
	    delay = loopBegin / 20;
	}
	if (delay == 9999999 || delay == 0) {
	    return 0;
	}
	for (int instrument = 0; instrument < 10; instrument++) {
	    if (instruments[instrument] != null) {
		instruments[instrument].begin -= delay * 20;
	    }
	}
	if (loopBegin < loopEnd) {
	    loopBegin -= delay * 20;
	    loopEnd -= delay * 20;
	}
	return delay;
    }

    private final Buffer encode(int loops) {
	int size = mix(loops);
	Track.buffer.offset = 0;
	Track.buffer.putInt(1380533830);
	Track.buffer.putLEInt(36 + size);
	Track.buffer.putInt(1463899717);
	Track.buffer.putInt(1718449184);
	Track.buffer.putLEInt(16);
	Track.buffer.putLEShort(1);
	Track.buffer.putLEShort(1);
	Track.buffer.putLEInt(22050);
	Track.buffer.putLEInt(22050);
	Track.buffer.putLEShort(1);
	Track.buffer.putLEShort(8);
	Track.buffer.putInt(1684108385);
	Track.buffer.putLEInt(size);
	Track.buffer.offset += size;
	return Track.buffer;
    }

    private final int mix(int loops) {
	int _dur = 0;
	for (int instrument = 0; instrument < 10; instrument++) {
	    if (instruments[instrument] != null && instruments[instrument].duration + instruments[instrument].begin > _dur) {
		_dur = instruments[instrument].duration + instruments[instrument].begin;
	    }
	}
	if (_dur == 0) {
	    return 0;
	}
	int nS = 22050 * _dur / 1000;
	int loopBegin = 22050 * this.loopBegin / 1000;
	int loopEnd = 22050 * this.loopEnd / 1000;
	if (loopBegin < 0 || loopBegin > nS || loopEnd < 0 || loopEnd > nS || loopBegin >= loopEnd) {
	    loops = 0;
	}
	int length = nS + (loopEnd - loopBegin) * (loops - 1);
	for (int position = 44; position < length + 44; position++) {
	    Track._buffer[position] = (byte) -128;
	}
	for (int instrument = 0; instrument < 10; instrument++) {
	    if (instruments[instrument] != null) {
		int dur = instruments[instrument].duration * 22050 / 1000;
		int offset = instruments[instrument].begin * 22050 / 1000;
		int[] samples = instruments[instrument].synthesize(dur, instruments[instrument].duration);
		for (int position = 0; position < dur; position++) {
		    Track._buffer[position + offset + 44] += (byte) (samples[position] >> 8);
		}
	    }
	}
	if (loops > 1) {
	    loopBegin += 44;
	    loopEnd += 44;
	    nS += 44;
	    length += 44;
	    int offset = length - nS;
	    for (int position = nS - 1; position >= loopEnd; position--) {
		Track._buffer[position + offset] = Track._buffer[position];
	    }
	    for (int loopCounter = 1; loopCounter < loops; loopCounter++) {
		offset = (loopEnd - loopBegin) * loopCounter;
		for (int position = loopBegin; position < loopEnd; position++) {
		    Track._buffer[position + offset] = Track._buffer[position];
		}
	    }
	    length -= 44;
	}
	return length;
    }
}
