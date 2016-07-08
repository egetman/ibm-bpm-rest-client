package ru.bpmink.bpm.model.task;

import com.google.gson.annotations.SerializedName;

public enum Action {

    @SerializedName("ACTION_CANCELCLAIM")
    CANCEL_CLAIM,
    @SerializedName("ACTION_CLAIM")
    CLAIM,
    @SerializedName("ACTION_COMPLETE")
    COMPLETE,
    @SerializedName("ACTION_CREATEMESSAGE")
    CREATE_MESSAGE,
    @SerializedName("ACTION_GETTASK")
    GET_TASK,
    @SerializedName("ACTION_GETUISETTINGS")
    GET_UI_SETTINGS,
    @SerializedName("ACTION_UPDATEDUEDATE")
    UPDATE_DUE_DATE,
    @SerializedName("ACTION_UPDATEPRIORITY")
    UPDATE_PRIORITY,
    @SerializedName("ACTION_SETOUTPUTMESSAGE")
    SET_OUTPUT_MESSAGE,
    @SerializedName("ACTION_INVITE")
    INVITE,
    @SerializedName("ACTION_REASSIGNTOUSER")
    REASSIGN_TO_USER,
    @SerializedName("ACTION_REASSIGNTOGROUP")
    REASSIGN_TO_GROUP

}
