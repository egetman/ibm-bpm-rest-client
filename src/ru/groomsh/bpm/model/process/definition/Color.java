package ru.groomsh.bpm.model.process.definition;

import com.google.gson.annotations.SerializedName;

/**
 * Color of the element in the diagramm.
 */
public enum Color {

	@SerializedName("Default")
	DEFAULT,
	@SerializedName("Red")
	RED,
	@SerializedName("Orange")
	ORANGE,
	@SerializedName("Yellow")
	YELLOW,
	@SerializedName("Green")
	GREEN,
	@SerializedName("Blue")
	BLUE,
	@SerializedName("Purple")
	PURPLE,
	@SerializedName("Gray")
	GRAY
	
}
