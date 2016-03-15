package ru.groomsh.bpm.model.task;

import com.google.gson.annotations.SerializedName;

public enum TaskPriority {
	
	@SerializedName("Lowest")
	LOWEST,
	@SerializedName("Low")
	LOW,
	@SerializedName("Normal")
	NORMAL,
	@SerializedName("High")
	HIGH,
	@SerializedName("Highest")
	HIGHEST
	
}
