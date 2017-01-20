package ru.bpmink.bpm.model.query;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;
import ru.bpmink.bpm.model.process.ExecutionState;
import ru.bpmink.bpm.model.process.ProcessState;
import ru.bpmink.bpm.model.task.TaskKind;
import ru.bpmink.util.Utils;

import java.util.Date;

/**
 * This class represents all parameters that you can obtain through query api.
 */
public class QueryResult extends RestEntity {

	public QueryResult() {
		
	}
	
	@SerializedName("CONTAINMENT_CTX_ID")
	private String containmentCtxId;
	
	@SerializedName("TASK.TKIID")
	private String taskTkiid;
	
	@SerializedName("PRIORITY")
	private Integer priority;
	
	@SerializedName("SNAPSHOT_NAME")
	private String snapshotName;
	
	@SerializedName("NAME")
	private String name;
	
	@SerializedName("TKIID")
	private String tkiid;
	
	@SerializedName("PI_DISPLAY_NAME")
	private String piDisplayName;
	
	@SerializedName("TAD_DISPLAY_NAME")
	private String tadDisplayName;
	
	@SerializedName("ACTIVATED")
	private Date activated;
	
	@SerializedName("PI_NAME")
	private String piName;
	
	@SerializedName("PI_STATE")
	private ProcessState state;
	
	@SerializedName("PI_STATUS")
	private ExecutionState status;
	
	@SerializedName("CLOSED_BY")
	private String closedBy;
	
	@SerializedName("ASSIGNED_TO_ROLE")
	private String assignedToRole; 
	
	@SerializedName("SENT_TIME")
	private Date sentTime;
	
	@SerializedName("OWNER")
	private String owner;
	
	@SerializedName("TAD_DESCRIPTION")
	private String tadDescription;
	
	@SerializedName("KIND")
	private TaskKind kind;
	
	@SerializedName("DUE")
	private Date due;
	
	@SerializedName("PROCESS_APP_ACRONYM")
	private String processAppAcronym;
	
	@SerializedName("SNAPSHOT_ID")
	private String snapshotId;
	
	@SerializedName("PT_PTID")
	private String ptPtid;
	
	@SerializedName("PT_NAME")
	private String ptName;
	
	@SerializedName("PI_PIID")
	private String piPiid;
	
	@SerializedName("PROCESS_INSTANCE.PIID")
	private String processInstancePiid;
	
	@SerializedName("ORIGINATOR")
	private String originator;
	
	public String getContainmentCtxId() {
		return containmentCtxId;
	}

	public String getTaskTkiid() {
		return taskTkiid;
	}

	public Integer getPriority() {
		return priority;
	}

	public String getSnapshotName() {
		return snapshotName;
	}

	public String getName() {
		return name;
	}

	public String getTkiid() {
		return tkiid;
	}

	public String getPiDisplayName() {
		return piDisplayName;
	}

	public String getTadDisplayName() {
		return tadDisplayName;
	}

	public Date getActivated() {
		return Utils.cloneDate(activated);
	}

	public String getPiName() {
		return piName;
	}

	public ProcessState getState() {
		return state;
	}

	public ExecutionState getStatus() {
		return status;
	}

	public String getOwner() {
		return owner;
	}

	public String getTadDescription() {
		return tadDescription;
	}

	public TaskKind getKind() {
		return kind;
	}

	public Date getDue() {
		return Utils.cloneDate(due);
	}

	public String getProcessAppAcronym() {
		return processAppAcronym;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getPtPtid() {
		return ptPtid;
	}

	public String getPiPiid() {
		return piPiid;
	}

	public String getOriginator() {
		return originator;
	}

	public void setContainmentCtxId(String containmentCtxId) {
		this.containmentCtxId = containmentCtxId;
	}

	public void setTaskTkiid(String taskTkiid) {
		this.taskTkiid = taskTkiid;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTkiid(String tkiid) {
		this.tkiid = tkiid;
	}

	public void setPiDisplayName(String piDisplayName) {
		this.piDisplayName = piDisplayName;
	}

	public void setTadDisplayName(String tadDisplayName) {
		this.tadDisplayName = tadDisplayName;
	}

	public void setActivated(Date activated) {
        this.activated = Utils.cloneDate(activated);
	}

	public void setPiName(String piName) {
		this.piName = piName;
	}

	public void setState(ProcessState state) {
		this.state = state;
	}

	public void setStatus(ExecutionState status) {
		this.status = status;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setTadDescription(String tadDescription) {
		this.tadDescription = tadDescription;
	}

	public void setKind(TaskKind kind) {
		this.kind = kind;
	}

	public void setDue(Date due) {
		this.due = Utils.cloneDate(due);
	}

	public void setProcessAppAcronym(String processAppAcronym) {
		this.processAppAcronym = processAppAcronym;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public void setPtPtid(String ptPtid) {
		this.ptPtid = ptPtid;
	}

	public void setPiPiid(String piPiid) {
		this.piPiid = piPiid;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}
	
	public String getClosedBy() {
		return closedBy;
	}

	public String getAssignedToRole() {
		return assignedToRole;
	}

	public Date getSentTime() {
        return Utils.cloneDate(sentTime);
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public void setAssignedToRole(String assignedToRole) {
		this.assignedToRole = assignedToRole;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = Utils.cloneDate(sentTime);
	}
	
	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	
	public String getProcessInstancePiid() {
		return processInstancePiid;
	}

	public void setProcessInstancePiid(String processInstancePiid) {
		this.processInstancePiid = processInstancePiid;
	}

}
