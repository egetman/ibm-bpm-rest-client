package ru.bpmink.bpm.model.task;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class TaskAction extends RestEntity {

    private static final List<Action> EMPTY_ACTIONS = Lists.newArrayList();

    //The task instance ID
    @SerializedName("tkiid")
    private String tkiid;

    //List of available actions for the task instance.
    @SerializedName("actions")
    private List<Action> actions = Lists.newArrayList();

    /**
     * @return The task instance ID
     */
    public String getTkiid() {
        return tkiid;
    }

    /**
     * @return List of available actions for the task instance.
     */
    public List<Action> getActions() {
        return MoreObjects.firstNonNull(actions, EMPTY_ACTIONS);
    }

    public void setTkiid(String tkiid) {
        this.tkiid = tkiid;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
