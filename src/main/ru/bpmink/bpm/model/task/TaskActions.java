package ru.bpmink.bpm.model.task;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

/**
 * This type represents actions that can be performed on task instances.
 */
public class TaskActions extends RestEntity {

    private static final List<String> EMPTY_TASK_ACTIONS = Lists.newArrayList();

    //This field specifies the primary identifier of the tasks; This will be set to "tkiid".
    @SerializedName("identifier")
    private String identifier;

    //A list of zero or more "TaskAction" structures.
    @SerializedName("tasks")
    private List<TaskAction> taskActions = Lists.newArrayList();

    /**
     * @return List of zero or more {@link ru.bpmink.bpm.model.task.TaskAction} structures.
     */
    public List<TaskAction> getTaskActions() {
        return taskActions;
    }

    /**
     * @return This primary identifier of the tasks; This will be set to "tkiid".
     */
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setTaskActions(List<TaskAction> taskActions) {
        this.taskActions = taskActions;
    }
}
