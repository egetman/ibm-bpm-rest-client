package ru.bpmink.bpm.model.process;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;
import ru.bpmink.bpm.model.process.definition.Diagram;
import ru.bpmink.bpm.model.task.TaskDetails;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProcessDetails extends RestEntity {
	
	private static final List<TaskDetails> EMPTY_TASKS = Lists.newArrayList();
	private static final List<String> EMPTY_COMMENTS = Lists.newArrayList();
	private static final List<Object> EMPTY_DOCUMENTS = Lists.newArrayList();
	private static final Map<String, Object> EMPTY_VARIABLES = Maps.newHashMap();
	private static final Diagram EMPTY_DIAGRAM = new Diagram();

	public ProcessDetails() {}
	
	//Details of the action performed if applicable.
	@SerializedName("actionDetails")
	private Object actionDetails;
	
	//Business data defined for the instance, including name, alias, type and value.
	@SerializedName("businessData")
	private Object businessData;

	//Creation time of the process instance.
	@SerializedName("creationTime")
	private Date creationTime;

	//List of comments on this instance.
	@SerializedName("comments")
	private List<String> comments = Lists.newArrayList();

	//Data of this instance in a json format.
	@SerializedName("data")
	private String data;

	//Description of the process instance.
	@SerializedName("description")
	private Object description;

	//BPD diagram of this instance, including existing tokens and associated task for the instance.
	@SerializedName("diagram")
	private Diagram diagram;

	//List of documents on this instance.
	@SerializedName("documents")
	private List<Object> documents;

	//Execution state of the process instance.
	@SerializedName("executionState")
	private ExecutionState executionState;
	
	//Execution tree associated with the process.
	@SerializedName("executionTree")
	private Object executionTree;

	//Error message for failed instances.
	@SerializedName("instanceError")
	private String instanceError;

	//Last time a property of the process instance changed.
	@SerializedName("lastModificationTime")
	private Date lastModificationTime;

	//Name of the process instance.
	@SerializedName("name")
	private String name;

	//ID of the process instance.
	@SerializedName("piid")
	private String piid;

	//ID of the process template this instance is derived from.
	@SerializedName("processTemplateID")
	private String processTemplateId;

	//Name of the process template this instance is derived from.
	@SerializedName("processTemplateName")
	private String processTemplateName;

	//Name of the process application.
	@SerializedName("processAppName")
	private String processAppName;

	//The process application acronym.
	@SerializedName("processAppAcronym")
	private String processAppAcronym;

	//Name of the snapshot associated with this process instance.
	@SerializedName("snapshotName")
	private String snapshotName;

	//ID of the snapshot associated with this process instance.
	@SerializedName("snapshotID")
	private String snapshotId;

	//The due date associated with the process instance.
	@SerializedName("dueDate")
	private Date dueDate;

	//The predicted due date associated with the process instance.
	@SerializedName("predictedDueDate")
	private Date predictedDueDate;

	//List of tasks with the same data model.
	@SerializedName("tasks")
	private List<TaskDetails> tasks;

	//Variable values in a json object.
	@SerializedName("variables")
	private Map<String, Object> variables = Maps.newHashMap();

	//State of the process instance.
	@SerializedName("state")
	private ProcessState state;
	
	/**
	 * @return Details of the action performed if applicable.
	 */
	public Object getActionDetails() {
		return actionDetails;
	}

	/**
	 * @return Business data defined for the instance, including name, alias, type and value.
	 */
	public Object getBusinessData() {
		return businessData;
	}

	/**
	 * @return Creation time of the process instance.
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @return List of comments on this instance.
	 */
	public List<String> getComments() {
		return MoreObjects.firstNonNull(comments, EMPTY_COMMENTS);
	}

	/**
	 * @return Data of this instance in a json format.
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return Description of the process instance.
	 */
	public Object getDescription() {
		return description;
	}

	/**
	 * @return BPD diagram of this instance, including existing tokens and associated task for the instance.
	 */
	public Diagram getDiagram() {
		return MoreObjects.firstNonNull(diagram, EMPTY_DIAGRAM);
	}

	/**
	 * @return List of documents on this instance.
	 */
	public List<Object> getDocuments() {
		return MoreObjects.firstNonNull(documents, EMPTY_DOCUMENTS);
	}

	/**
	 * @return Execution state of the process instance.
	 */
	public ExecutionState getExecutionState() {
		return executionState;
	}

	/**
	 * @return Execution tree associated with the process.
	 */
	public Object getExecutionTree() {
		return executionTree;
	}

	/**
	 * @return Error message for failed instances.
	 */
	public String getInstanceError() {
		return instanceError;
	}

	/**
	 * @return Last time a property of the process instance changed.
	 */
	public Date getLastModificationTime() {
		return lastModificationTime;
	}

	/**
	 * @return Name of the process instance.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return ID of the process instance.
	 */
	public String getPiid() {
		return piid;
	}

	/**
	 * @return ID of the process template this instance is derived from.
	 */
	public String getProcessTemplateId() {
		return processTemplateId;
	}

	/**
	 * @return Name of the process template this instance is derived from.
	 */
	public String getProcessTemplateName() {
		return processTemplateName;
	}

	/**
	 * @return Name of the process application.
	 */
	public String getProcessAppName() {
		return processAppName;
	}

	/**
	 * @return The process application acronym.
	 */
	public String getProcessAppAcronym() {
		return processAppAcronym;
	}

	/**
	 * @return Name of the snapshot associated with this process instance.
	 */
	public String getSnapshotName() {
		return snapshotName;
	}

	/**
	 * @return ID of the snapshot associated with this process instance.
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @return The due date associated with the process instance.
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return The predicted due date associated with the process instance.
	 */
	public Date getPredictedDueDate() {
		return predictedDueDate;
	}

	/**
	 * @return List of tasks with the same data model.
	 */
	public List<TaskDetails> getTasks() {
		return MoreObjects.firstNonNull(tasks, EMPTY_TASKS);
	}

	/**
	 * @return Variable values in a json object.
	 */
	public Map<String, Object> getVariables() {
		return MoreObjects.firstNonNull(variables, EMPTY_VARIABLES);
	}

	/**
	 * @return State of the process instance.
	 */
	public ProcessState getState() {
		return state;
	}

	public void setActionDetails(Object actionDetails) {
		this.actionDetails = actionDetails;
	}

	public void setBusinessData(Object businessData) {
		this.businessData = businessData;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public void setDocuments(List<Object> documents) {
		this.documents = documents;
	}

	public void setExecutionState(ExecutionState executionState) {
		this.executionState = executionState;
	}

	public void setExecutionTree(Object executionTree) {
		this.executionTree = executionTree;
	}

	public void setInstanceError(String instanceError) {
		this.instanceError = instanceError;
	}

	public void setLastModificationTime(Date lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}

	public void setProcessTemplateId(String processTemplateId) {
		this.processTemplateId = processTemplateId;
	}

	public void setProcessTemplateName(String processTemplateName) {
		this.processTemplateName = processTemplateName;
	}

	public void setProcessAppName(String processAppName) {
		this.processAppName = processAppName;
	}

	public void setProcessAppAcronym(String processAppAcronym) {
		this.processAppAcronym = processAppAcronym;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setPredictedDueDate(Date predictedDueDate) {
		this.predictedDueDate = predictedDueDate;
	}

	public void setTasks(List<TaskDetails> tasks) {
		this.tasks = tasks;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void setState(ProcessState state) {
		this.state = state;
	}

}
