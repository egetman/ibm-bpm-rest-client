package ru.bpmink.bpm.model.process.definition;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class Lane extends RestEntity {

	public Lane() {}
	
	//Name of swimlane
	@SerializedName("name")
	private String name;
	
	//Height of lane
	@SerializedName("height")
	private Integer height;
	
	//Shows if the current lane is system lane
	@SerializedName("system")
	private Boolean isSystemLane;

	/**
	 * @return Name of swimlane.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Height of lane
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @return Shows if the current lane is system lane
	 */
	public Boolean isSystemLane() {
		return isSystemLane;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setSystemLane(Boolean isSystemLane) {
		this.isSystemLane = isSystemLane;
	}

}
