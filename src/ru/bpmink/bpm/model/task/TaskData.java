package ru.bpmink.bpm.model.task;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.Map;

/**
 * This type represents the result obtained from evaluating a javascript expression
 * within the context of a currently running service
 */
public class TaskData extends RestEntity {

	//A string which represent the result obtained from evaluating a javascript expression within the context of a currently running service.
	@SerializedName("result")
	private String result;

	//Data information stored in an actual Map.
	@SerializedName("resultMap")
	private Map<String, Object> resultMap = Maps.newHashMap();

	/**
	 * @return A string which represent the result obtained from evaluating a javascript expression within the context of a currently running service.
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return Data information stored in an actual Map.
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

}
