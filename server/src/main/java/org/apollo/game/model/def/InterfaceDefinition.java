package org.apollo.game.model.def;

/**
 * Represents a single interface definition.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class InterfaceDefinition {

	/**
	 * The interface definitions.
	 */
	private static InterfaceDefinition[] definitions;

	/**
	 * The id of this interface.
	 */
	private final int id;

	/**
	 * The parent id of this interface.
	 */
	private int parentId;

	/**
	 * The type of this interface.
	 */
	private int type;

	/**
	 * The alpha of this interface.
	 */
	private byte alpha;

	/**
	 * The width of this interface.
	 */
	private int width;

	/**
	 * The height of this interface.
	 */
	private int height;

	/**
	 * The zoom of this interface.
	 */
	private int zoom;

	/**
	 * The x rotation of this interface.
	 */
	private int rotationX;

	/**
	 * The y rotation of this interface.
	 */
	private int rotationY;

	/**
	 * Whether or not this interface is hidden until hovered.
	 */
	private boolean hiddenUntilHovered;

	/**
	 * The enabled animation on this interface.
	 */
	private int enabledAnimation;

	/**
	 * The disabled animation on this interface.
	 */
	private int disabledAnimation;

	/**
	 * The condition types on this interface.
	 */
	private int[] conditionTypes;

	/**
	 * The condition values of this interface.
	 */
	private int[] conditionValues;

	/**
	 * The type of content this interfaces contains.
	 */
	private int contentType;

	/**
	 * An array of image x coordinates on this interface.
	 */
	private int[] imageX;

	/**
	 * An array of image y coordinates on this interface.
	 */
	private int[] imageY;

	/**
	 * The color of this interface when enabled and hovered.
	 */
	private int enabledHoveredColor;

	/**
	 * The color of this interface when disabled and hovered.
	 */
	private int disabledHoveredColor;

	/**
	 * The type of action this interface performs.
	 */
	private int actionType;

	/**
	 * The name of a magic spell on this interface.
	 */
	private String spellName;

	/**
	 * The spell usable on this interface.
	 */
	private int spellUsableOn;

	/**
	 * The enabled color of this interface.
	 */
	private int enabledColor;

	/**
	 * The disabled color of this interface.
	 */
	private int disabledColor;

	/**
	 * This interfaces tooltip.
	 */
	private String tooltip;

	/**
	 * This interfaces selecton action name.
	 */
	private String selectedActionName;

	/**
	 * Whether or not the type face font is centered on this interface.
	 */
	private boolean typeFaceCentered;

	/**
	 * Whether or not the type face font is shadowed on this interface.
	 */
	private boolean typeFaceShadowed;

	/**
	 * The maximum scroll position.
	 */
	private int scrollLimit;

	/**
	 * The actions this interfaces has.
	 */
	private String[] actions;

	/**
	 * The opcodes of this interface.
	 */
	private int[][] opcodes;

	/**
	 * Whether or not this interface is filled.
	 */
	private boolean filled;

	/**
	 * The enabled text on this interface.
	 */
	private String enabledText;

	/**
	 * The disabled text on this interface.
	 */
	private String disabledText;

	/**
	 * The popup hover of this interface,
	 */
	private int hoveredPopup;

	/**
	 * The item sprite x padding on this interface.
	 */
	private int itemSpritePadsX;

	/**
	 * The item sprite y padding on this interface.
	 */
	private int itemSpritePadsY;

	/**
	 * The type of model on this interface.
	 */
	private int modelType;

	/**
	 * The id of the model on this interface.
	 */
	private int modelId;

	/**
	 * The enabled model type on this interface.
	 */
	private int enabledModelType;

	/**
	 * The enabled model id on this interface.
	 */
	private int enabledModelId;

	/**
	 * Whether or not dragged items are deleted when dragged on this interface.
	 */
	private boolean itemDeletesDraged;

	/**
	 * Whether or not the item on this interface is usable.
	 */
	private boolean itemUsable;

	/**
	 * Whether or not the item on this interface is swapable.
	 */
	private boolean itemSwapable;

	/**
	 * Whether or not this interface is an inventory.
	 */
	private boolean isInventory;

	/**
	 * The items on this interface.
	 */
	private int[] items;

	/**
	 * The amount of items on this interface.
	 */
	private int[] itemAmounts;

	/**
	 * The children on this interface.
	 */
	private int[] children;

	/**
	 * The x coordinates of the children on this interface.
	 */
	private int[] childrenX;

	/**
	 * The y coordinates of the children on this interface.
	 */
	private int[] childrenY;

	/**
	 * Constructs a new {@link InterfaceDefinition} with the specified id.
	 *
	 * @param id The interfaces id.
	 */
	public InterfaceDefinition(int id) {
		this.id = id;
	}

	/**
	 * Initializes the class with the specified set of definitions.
	 *
	 * @param definitions The definitions.
	 * @throws RuntimeException if there is an id mismatch.
	 */
	public static void init(InterfaceDefinition[] definitions) {
		InterfaceDefinition.definitions = definitions;
		for (int id = 0; id < definitions.length; id++) {
			InterfaceDefinition def = definitions[id];
			if (def != null && def.getId() != id) {
				throw new RuntimeException("Interface definition id mismatch, id: " + def.getId() + " expected: " + id);
			}
		}
	}

	/**
	 * Gets the total number of interfaces.
	 *
	 * @return The total number of interfaces.
	 */
	public static int count() {
		return definitions.length;
	}

	/**
	 * Gets the interface definition for the specified id.
	 *
	 * @param id The id.
	 * @return The definition.
	 * @throws IndexOutOfBoundsException if the id is out of bounds.
	 */
	public static InterfaceDefinition forId(int id) {
		if (id < 0 || id >= definitions.length) {
			throw new IndexOutOfBoundsException();
		}
		return definitions[id];
	}

	/**
	 * Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the parentId.
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Sets a new value for parentId.
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * Returns the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets a new value for type.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets a new value for width.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets a new value for height.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the alpha.
	 */
	public byte getAlpha() {
		return alpha;
	}

	/**
	 * Sets a new value for alpha.
	 */
	public void setAlpha(byte alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the hoveredPopup.
	 */
	public int getHoveredPopup() {
		return hoveredPopup;
	}

	/**
	 * Sets a new value for hoveredPopup.
	 */
	public void setHoveredPopup(int hoveredPopup) {
		this.hoveredPopup = hoveredPopup;
	}

	/**
	 * Returns the actionType.
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * Sets a new value for actionType.
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns the contentType.
	 */
	public int getContentType() {
		return contentType;
	}

	/**
	 * Sets a new value for contentType.
	 */
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	/**
	 * Returns the conditionTypes.
	 */
	public int[] getConditionTypes() {
		return conditionTypes;
	}

	/**
	 * Sets a new value for conditionTypes.
	 */
	public void setConditionTypes(int[] conditionTypes) {
		this.conditionTypes = conditionTypes;
	}

	/**
	 * Returns the conditionValues.
	 */
	public int[] getConditionValues() {
		return conditionValues;
	}

	/**
	 * Sets a new value for conditionValues.
	 */
	public void setConditionValues(int[] conditionValues) {
		this.conditionValues = conditionValues;
	}

	/**
	 * Returns the opcodes.
	 */
	public int[][] getOpcodes() {
		return opcodes;
	}

	/**
	 * Sets a new value for opcodes.
	 */
	public void setOpcodes(int[][] opcodes) {
		this.opcodes = opcodes;
	}

	/**
	 * Returns the scrollLimit.
	 */
	public int getScrollLimit() {
		return scrollLimit;
	}

	/**
	 * Sets a new value for scrollLimit.
	 */
	public void setScrollLimit(int scrollLimit) {
		this.scrollLimit = scrollLimit;
	}

	/**
	 * Returns the children.
	 */
	public int[] getChildren() {
		return children;
	}

	/**
	 * Sets a new value for children.
	 */
	public void setChildren(int[] children) {
		this.children = children;
	}

	/**
	 * Returns the childrenX.
	 */
	public int[] getChildrenX() {
		return childrenX;
	}

	/**
	 * Sets a new value for childrenX.
	 */
	public void setChildrenX(int[] childrenX) {
		this.childrenX = childrenX;
	}

	/**
	 * Returns the childrenY.
	 */
	public int[] getChildrenY() {
		return childrenY;
	}

	/**
	 * Sets a new value for childrenY.
	 */
	public void setChildrenY(int[] childrenY) {
		this.childrenY = childrenY;
	}

	/**
	 * Returns the hiddenUntilHovered.
	 */
	public boolean isHiddenUntilHovered() {
		return hiddenUntilHovered;
	}

	/**
	 * Sets a new value for hiddenUntilHovered.
	 */
	public void setHiddenUntilHovered(boolean hiddenUntilHovered) {
		this.hiddenUntilHovered = hiddenUntilHovered;
	}

	/**
	 * Returns the actions.
	 */
	public String[] getActions() {
		return actions;
	}

	/**
	 * Sets a new value for actions.
	 */
	public void setActions(String[] actions) {
		this.actions = actions;
	}

	/**
	 * Returns the itemSpritePadsX.
	 */
	public int getItemSpritePadsX() {
		return itemSpritePadsX;
	}

	/**
	 * Sets a new value for itemSpritePadsX.
	 */
	public void setItemSpritePadsX(int itemSpritePadsX) {
		this.itemSpritePadsX = itemSpritePadsX;
	}

	/**
	 * Returns the itemSpritePadsY.
	 */
	public int getItemSpritePadsY() {
		return itemSpritePadsY;
	}

	/**
	 * Sets a new value for itemSpritePadsY.
	 */
	public void setItemSpritePadsY(int itemSpritePadsY) {
		this.itemSpritePadsY = itemSpritePadsY;
	}

	/**
	 * Returns the itemDeletesDraged.
	 */
	public boolean isItemDeletesDraged() {
		return itemDeletesDraged;
	}

	/**
	 * Sets a new value for itemDeletesDraged.
	 */
	public void setItemDeletesDraged(boolean itemDeletesDraged) {
		this.itemDeletesDraged = itemDeletesDraged;
	}

	/**
	 * Returns the itemUsable.
	 */
	public boolean isItemUsable() {
		return itemUsable;
	}

	/**
	 * Sets a new value for itemUsable.
	 */
	public void setItemUsable(boolean itemUsable) {
		this.itemUsable = itemUsable;
	}

	/**
	 * Returns the itemSwapable.
	 */
	public boolean isItemSwapable() {
		return itemSwapable;
	}

	/**
	 * Sets a new value for itemSwapable.
	 */
	public void setItemSwapable(boolean itemSwapable) {
		this.itemSwapable = itemSwapable;
	}

	/**
	 * Returns the isInventory.
	 */
	public boolean isInventory() {
		return isInventory;
	}

	/**
	 * Sets a new value for isInventory.
	 */
	public void setInventory(boolean isInventory) {
		this.isInventory = isInventory;
	}

	/**
	 * Returns the items.
	 */
	public int[] getItems() {
		return items;
	}

	/**
	 * Sets a new value for items.
	 */
	public void setItems(int[] items) {
		this.items = items;
	}

	/**
	 * Returns the itemAmounts.
	 */
	public int[] getItemAmounts() {
		return itemAmounts;
	}

	/**
	 * Sets a new value for itemAmounts.
	 */
	public void setItemAmounts(int[] itemAmounts) {
		this.itemAmounts = itemAmounts;
	}

	/**
	 * Returns the filled.
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * Sets a new value for filled.
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Returns the imageX.
	 */
	public int[] getImageX() {
		return imageX;
	}

	/**
	 * Sets a new value for imageX.
	 */
	public void setImageX(int[] imageX) {
		this.imageX = imageX;
	}

	/**
	 * Returns the imageY.
	 */
	public int[] getImageY() {
		return imageY;
	}

	/**
	 * Sets a new value for imageY.
	 */
	public void setImageY(int[] imageY) {
		this.imageY = imageY;
	}

	/**
	 * Returns the typeFaceCentered.
	 */
	public boolean isTypeFaceCentered() {
		return typeFaceCentered;
	}

	/**
	 * Sets a new value for typeFaceCentered.
	 */
	public void setTypeFaceCentered(boolean typeFaceCentered) {
		this.typeFaceCentered = typeFaceCentered;
	}

	/**
	 * Returns the typeFaceShadowed.
	 */
	public boolean isTypeFaceShadowed() {
		return typeFaceShadowed;
	}

	/**
	 * Sets a new value for typeFaceShadowed.
	 */
	public void setTypeFaceShadowed(boolean typeFaceShadowed) {
		this.typeFaceShadowed = typeFaceShadowed;
	}

	/**
	 * Returns the enabledText.
	 */
	public String getEnabledText() {
		return enabledText;
	}

	/**
	 * Sets a new value for enabledText.
	 */
	public void setEnabledText(String enabledText) {
		this.enabledText = enabledText;
	}

	/**
	 * Returns the disabledText.
	 */
	public String getDisabledText() {
		return disabledText;
	}

	/**
	 * Sets a new value for disabledText.
	 */
	public void setDisabledText(String disabledText) {
		this.disabledText = disabledText;
	}

	/**
	 * Returns the disabledColor.
	 */
	public int getDisabledColor() {
		return disabledColor;
	}

	/**
	 * Sets a new value for disabledColor.
	 */
	public void setDisabledColor(int disabledColor) {
		this.disabledColor = disabledColor;
	}

	/**
	 * Returns the enabledColor.
	 */
	public int getEnabledColor() {
		return enabledColor;
	}

	/**
	 * Sets a new value for enabledColor.
	 */
	public void setEnabledColor(int enabledColor) {
		this.enabledColor = enabledColor;
	}

	/**
	 * Returns the enabledHoveredColor.
	 */
	public int getEnabledHoveredColor() {
		return enabledHoveredColor;
	}

	/**
	 * Sets a new value for enabledHoveredColor.
	 */
	public void setEnabledHoveredColor(int enabledHoveredColor) {
		this.enabledHoveredColor = enabledHoveredColor;
	}

	/**
	 * Returns the disabledHoveredColor.
	 */
	public int getDisabledHoveredColor() {
		return disabledHoveredColor;
	}

	/**
	 * Sets a new value for disabledHoveredColor.
	 */
	public void setDisabledHoveredColor(int disabledHoveredColor) {
		this.disabledHoveredColor = disabledHoveredColor;
	}

	/**
	 * Returns the modelType.
	 */
	public int getModelType() {
		return modelType;
	}

	/**
	 * Sets a new value for modelType.
	 */
	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	/**
	 * Returns the enabledModelType.
	 */
	public int getEnabledModelType() {
		return enabledModelType;
	}

	/**
	 * Sets a new value for enabledModelType.
	 */
	public void setEnabledModelType(int enabledModelType) {
		this.enabledModelType = enabledModelType;
	}

	/**
	 * Returns the enabledModelId.
	 */
	public int getEnabledModelId() {
		return enabledModelId;
	}

	/**
	 * Sets a new value for enabledModelId.
	 */
	public void setEnabledModelId(int enabledModelId) {
		this.enabledModelId = enabledModelId;
	}

	/**
	 * Returns the modelId.
	 */
	public int getModelId() {
		return modelId;
	}

	/**
	 * Sets a new value for modelId.
	 */
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	/**
	 * Returns the zoom.
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * Sets a new value for zoom.
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * Returns the rotationX.
	 */
	public int getRotationX() {
		return rotationX;
	}

	/**
	 * Sets a new value for rotationX.
	 */
	public void setRotationX(int rotationX) {
		this.rotationX = rotationX;
	}

	/**
	 * Returns the rotationY.
	 */
	public int getRotationY() {
		return rotationY;
	}

	/**
	 * Sets a new value for rotationY.
	 */
	public void setRotationY(int rotationY) {
		this.rotationY = rotationY;
	}

	/**
	 * Returns the enabledAnimation.
	 */
	public int getEnabledAnimation() {
		return enabledAnimation;
	}

	/**
	 * Sets a new value for enabledAnimation.
	 */
	public void setEnabledAnimation(int enabledAnimation) {
		this.enabledAnimation = enabledAnimation;
	}

	/**
	 * Returns the disabledAnimation.
	 */
	public int getDisabledAnimation() {
		return disabledAnimation;
	}

	/**
	 * Sets a new value for disabledAnimation.
	 */
	public void setDisabledAnimation(int disabledAnimation) {
		this.disabledAnimation = disabledAnimation;
	}

	/**
	 * Returns the selectedActionName.
	 */
	public String getSelectedActionName() {
		return selectedActionName;
	}

	/**
	 * Sets a new value for selectedActionName.
	 */
	public void setSelectedActionName(String selectedActionName) {
		this.selectedActionName = selectedActionName;
	}

	/**
	 * Returns the spellName.
	 */
	public String getSpellName() {
		return spellName;
	}

	/**
	 * Sets a new value for spellName.
	 */
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	/**
	 * Returns the spellUsableOn.
	 */
	public int getSpellUsableOn() {
		return spellUsableOn;
	}

	/**
	 * Sets a new value for spellUsableOn.
	 */
	public void setSpellUsableOn(int spellUsableOn) {
		this.spellUsableOn = spellUsableOn;
	}

	/**
	 * Returns the tooltip.
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * Sets a new value for tooltip.
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

}