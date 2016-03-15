package ru.groomsh.bpm.model.process;

import com.google.gson.annotations.SerializedName;

public enum ExecutionState {
	
	@SerializedName("New")
	NEW,
	@SerializedName("Active")
	ACTIVE,
	@SerializedName("Completed")
	COMPLETED,
	@SerializedName("Failed")
	FAILED,
	@SerializedName("Suspended")
	SUSPENDED,
	@SerializedName("Terminated")
	TERMINATED
	
}
