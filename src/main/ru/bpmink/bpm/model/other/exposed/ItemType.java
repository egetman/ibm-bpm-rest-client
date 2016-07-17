package ru.bpmink.bpm.model.other.exposed;

import com.google.gson.annotations.SerializedName;

public enum ItemType {
	
	@SerializedName("process")
	PROCESS, 
	@SerializedName("service")
	SERVICE, 
	@SerializedName("scoreboard")
	SCOREBOARD, 
	@SerializedName("report")
	REPORT
	
}
