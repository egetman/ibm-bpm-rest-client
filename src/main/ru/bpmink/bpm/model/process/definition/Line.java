package ru.bpmink.bpm.model.process.definition;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class Line extends RestEntity {

	public Line() {}
	
	//Direction
	@SerializedName("to")
	private String to;
	
	//Don't know what it
	@SerializedName("points")
	private String points;
	
	@SerializedName("tokenID")
	private Object tokenId;

	/**
	 * @return Direction.
	 */
	public String getTo() {
		return to;
	}

	public String getPoints() {
		return points;
	}

	public Object getTokenId() {
		return tokenId;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void setTokenId(Object tokenId) {
		this.tokenId = tokenId;
	}
	
}
