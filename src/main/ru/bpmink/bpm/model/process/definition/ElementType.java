package ru.bpmink.bpm.model.process.definition;

import com.google.gson.annotations.SerializedName;

public enum ElementType {

	@SerializedName("activity")
	ACTIVITY,
	@SerializedName("event")
	EVENT,
	@SerializedName("gateway")
	GATEWAY
}
