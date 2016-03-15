package ru.groomsh.bpm.model.task;

import com.google.gson.annotations.SerializedName;

public enum TaskStatus {

	@SerializedName("New")
	NEW,
	@SerializedName("Received")
	RECEIVED,
	@SerializedName("Replied")
	REPLIED,
	@SerializedName("Forwarded")
	FORWARDED,
	@SerializedName("Sent")
	SENT,
	@SerializedName("Actioned")
	ACTIONED,
	@SerializedName("Closed")
	CLOSED,
	@SerializedName("Special")
	SPECIAL,
	@SerializedName("Deleted")
	DELETED,
	@SerializedName("Alert")
	ALERT,
	@SerializedName("Help_Request")
	HELP_REQUEST,
	@SerializedName("Comment")
	COMMENT,
	@SerializedName("Answered_Help_Request")
	ANSWERED_HELP_REQUEST,
	@SerializedName("Ignored_Help_Request")
	IGNORED_HELP_REQUEST
	
}
