package ru.bpmink.bpm.model.other.processapp;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class ProcessAppsBody extends RestEntity {

	private static final List<ProcessApp> EMPTY_LIST = Lists.newArrayList();
	
	public ProcessAppsBody() {}
	
	//List of process apps installed in the system.
	@SerializedName("processAppsList")
	private List<ProcessApp> processAppsList = Lists.newArrayList();
	
	//To avoid null checks after deserialization.
	public List<ProcessApp> getProcessAppsList() {
		return MoreObjects.firstNonNull(processAppsList, EMPTY_LIST);
	}

	
}
