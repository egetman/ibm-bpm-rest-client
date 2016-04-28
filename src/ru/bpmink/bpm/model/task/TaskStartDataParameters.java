package ru.bpmink.bpm.model.task;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.Map;

public class TaskStartDataParameters extends RestEntity {

	private static final Map<String, Object> EMPTY_MAP = Maps.newHashMap();
	
	public TaskStartDataParameters() {
	
	}
	
	//The "dataMap" key has a value that is a map containing the variables and values of the task.
	@SerializedName("dataMap")
	private Map<String, Object> dataMap = Maps.newHashMap();
	
	@SerializedName("serviceStatus")
	private String serviceStatus;
	
	//The "data" key has a value that is json string representing the variables and values of the task.
	@SerializedName("data")
	private String data;
	
	@SerializedName("step")
	private String step;
	
	//Identifier of current task.
	@SerializedName("key")
	private String key;

	/**
	 * @return Map, which contains the variables and values of the task.
	 */
	public Map<String, Object> getDataMap() {
		return MoreObjects.firstNonNull(dataMap, EMPTY_MAP);
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	/**
	 * @return The value that is json string representing the variables and values of the task.
	 */
	public String getData() {
		return data;
	}

	public String getStep() {
		return step;
	}

	/**
	 * @return Identifier of current task.
	 */
	public String getKey() {
		return key;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
