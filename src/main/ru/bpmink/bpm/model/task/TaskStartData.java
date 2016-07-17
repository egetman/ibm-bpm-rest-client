package ru.bpmink.bpm.model.task;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class TaskStartData extends RestEntity {
	
	private static final TaskStartDataParameters NULL_OBJECT = new TaskStartDataParameters();
	
	public TaskStartData() {
		
	}
	
	@SerializedName("return")
	private TaskStartDataParameters parameters;

	public TaskStartDataParameters getParameters() {
		return MoreObjects.firstNonNull(parameters, NULL_OBJECT);
	}

	public void setParameters(TaskStartDataParameters parameters) {
		this.parameters = parameters;
	}

}
