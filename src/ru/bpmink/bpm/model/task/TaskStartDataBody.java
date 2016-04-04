package ru.bpmink.bpm.model.task;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class TaskStartDataBody extends RestEntity {
	
	private static final TaskStartDataBodyParameters NULL_OBJECT = new TaskStartDataBodyParameters();
	
	public TaskStartDataBody() {
		
	}
	
	@SerializedName("return")
	private TaskStartDataBodyParameters parameters;

	public TaskStartDataBodyParameters getParameters() {
		return MoreObjects.firstNonNull(parameters, NULL_OBJECT);
	}

	public void setParameters(TaskStartDataBodyParameters parameters) {
		this.parameters = parameters;
	}

}
