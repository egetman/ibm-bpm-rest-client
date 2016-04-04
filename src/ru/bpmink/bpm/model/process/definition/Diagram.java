package ru.bpmink.bpm.model.process.definition;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

/**
 * The 'diagram' part of the BPD model.
 */
public class Diagram extends RestEntity {
	
	private static final List<String> EMPTY_MILESTONES = Lists.newArrayList(); 
	private static final List<Lane> EMPTY_LANES = Lists.newArrayList(); 
	private static final List<Step> EMPTY_STEPS = Lists.newArrayList(); 


	public Diagram() {}
	
	//The process application ID (short name).
	@SerializedName("processAppID")
	private String processAppId;
	
	//A list of zero or more milestones associated with the process. A milestone represents a phase of process execution.
	@SerializedName("milestone")
	private List<String> milestone = Lists.newArrayList();
	
	//A list of zero or more steps associated with the process.
	@SerializedName("step")
	private List<Step> steps = Lists.newArrayList();
	
	//A list of zero or more lanes associated with the process.  A 'lane' typically represents a department within a business organization.
	@SerializedName("lanes")
	private List<Lane> lanes = Lists.newArrayList();

	/**
	 * @return The process application ID (short name).
	 */
	public String getProcessAppId() {
		return processAppId;
	}

	/**
	 * @return A list of zero or more milestones associated with the process. A milestone represents a phase of process execution.
	 */
	public List<String> getMilestone() {
		return MoreObjects.firstNonNull(milestone, EMPTY_MILESTONES);
	}

	/**
	 * @return A list of zero or more steps associated with the process.
	 */
	public List<Step> getSteps() {
		return MoreObjects.firstNonNull(steps, EMPTY_STEPS);
	}

	/**
	 * @return A list of zero or more lanes associated with the process.  A 'lane' typically represents a department within a business organization.
	 */
	public List<Lane> getLanes() {
		return MoreObjects.firstNonNull(lanes, EMPTY_LANES);
	}

	public void setProcessAppId(String processAppId) {
		this.processAppId = processAppId;
	}

	public void setMilestone(List<String> milestone) {
		this.milestone = milestone;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}

}
