package ru.bpmink.bpm.model.task;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class Collaboration extends RestEntity {

    //The task is under collaboration or not.
    @SerializedName("status")
    private Boolean underCollaboration;

    //If the service status is "coach", this field contains a list of available allowedUsers (end point).
    @SerializedName("allowedUsers")
    private List<String> allowedUsers = Lists.newArrayList();

    //If the service status is "coach", this field contains a list of available currentUsers (end point).
    @SerializedName("currentUsers")
    private List<String> currentUsers = Lists.newArrayList();

    //This will hold the url for invited user for any collaborated task.
    @SerializedName("taskURL")
    private String taskUrl;

    /**
     * @return True if task is under collaboration, false otherwise.
     */
    public Boolean isUnderCollaboration() {
        return underCollaboration;
    }

    public void setUnderCollaboration(Boolean underCollaboration) {
        this.underCollaboration = underCollaboration;
    }

    /**
     * @return A list of available allowedUsers (end point) if the service status is "coach".
     */
    public List<String> getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(List<String> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    /**
     * @return A list of available currentUsers (end point) if the service status is "coach".
     */
    public List<String> getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(List<String> currentUsers) {
        this.currentUsers = currentUsers;
    }

    /**
     * @return Url for invited user for any collaborated task.
     */
    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }
}
