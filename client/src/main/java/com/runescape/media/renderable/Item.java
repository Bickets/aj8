package com.runescape.media.renderable;

import com.runescape.cache.def.ItemDefinition;

public class Item extends Renderable {

	public int itemId;
	public int itemCount;

	@Override
	public final Model getRotatedModel() {
		ItemDefinition def = ItemDefinition.getDefinition(itemId);
		return def.getAmountModel(itemCount);
	}
}
