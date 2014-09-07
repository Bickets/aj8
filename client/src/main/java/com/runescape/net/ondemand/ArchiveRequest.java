package com.runescape.net.ondemand;

import com.runescape.collection.CacheableNode;

public class ArchiveRequest extends CacheableNode {

	public int type;
	public byte[] buffer;
	public int id;
	public boolean immediate = true;
	protected int cyclesSinceSend;
}
