package ru.bpmink.bpm.api.client;

import ru.bpmink.bpm.model.common.RestEntity;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.task.TaskActions;
import ru.bpmink.bpm.model.task.TaskClientSettings;
import ru.bpmink.bpm.model.task.TaskData;
import ru.bpmink.bpm.model.task.TaskDetails;
import ru.bpmink.bpm.model.task.TaskPriority;
import ru.bpmink.bpm.model.task.TaskStartData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Map;


//TODO: Add full api possibilities
/**
* Client for task api actions.
*/
public interface TaskClient {

	/**
	 * Retrieves the details of a task by given {@literal tkiid} (Task instance id).
	 *
	 * @param tkiid The id of the task instance to be retrieved.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> getTask(@Nonnull String tkiid);
	
	/**
	 * Start new task instance.
     * The input variables defined on the task will be set according to the definitions in the associated business
     * process instance.
     * <p>The task will proceed until the first coach is encountered.</p>
     *
	 * @param tkiid The id of the task instance to start.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance which holds
     *      {@link ru.bpmink.bpm.model.task.TaskStartData}: a list of attribute/value pairs.
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskStartData> startTask(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task to the current user.
     *
	 * @param tkiid The id of the task instance to be started.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> assignTaskToMe(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task back to the original task owner.
     *
	 * @param tkiid The id of the task instance to be assigned.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> assignTaskBack(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task to another user. 
	 * If userName is null, reassigned task back to the original task owner.
     *
	 * @param tkiid The id of the task instance to be assigned.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> assignTaskToUser(@Nonnull String tkiid, @Nullable String userName);
	
	/**
	 * Assign the specified task to a group. 
	 * If groupName is null, reassigned task back to the original task owner.
     *
	 * @param tkiid The id of the task instance to be assigned.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> assignTaskToGroup(@Nonnull String tkiid, @Nullable String groupName);
	
	/**
	 * Finish the specified task.
     * <p><b>Input parameters {@literal NOT} propagated to enclosing process.</b></p>
     * Use Task api #setTaskData(String, Map) for that purpose.
     *
	 * @param tkiid The id of the task instance to be finished.
	 * @param input Parameters to finish specified task/activity.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskDetails> completeTask(@Nonnull String tkiid, @Nullable Map<String, Object> input);

	/**
	 * Cancel the specified task.
     *
	 * @param tkiid The id of the task instance to be cancelled.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance with empty response:
     *      {@link ru.bpmink.bpm.model.common.RestEntity}.
     *      If the call was unsuccessful, error	details will be filled with information.
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<RestEntity> cancelTask(@Nonnull String tkiid);

	/**
	 * Get data from specified task.
     *
	 * @param tkiid The id of the task instance.
	 * @param fields Comma-separated list of fields.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds task data information:
     *      {@link ru.bpmink.bpm.model.task.TaskData}
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestRootEntity<TaskData> getTaskData(@Nonnull String tkiid, @Nullable String fields);

    /**
     * Update a task's priority.
     *
     * @param tkiid The id of the task instance to be updated.
     * @param priority Is new task priority {@link ru.bpmink.bpm.model.task.TaskPriority}
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails} with updated values.
     * @throws IllegalArgumentException if tkiid or priority is null
     */
    RestRootEntity<TaskDetails> updateTaskPriority(@Nonnull String tkiid, @Nonnull TaskPriority priority);


    /**
     * Update a task's due time.
     *
     * @param tkiid The id of the task instance to be updated.
     * @param dueTime Is new task due time.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds detailed task information:
     *      {@link ru.bpmink.bpm.model.task.TaskDetails} with updated values.
     * @throws IllegalArgumentException if tkiid or dueTime is null
     */
    RestRootEntity<TaskDetails> updateTaskDueTime(@Nonnull String tkiid, @Nonnull Date dueTime);

	/**
	 * Use this method to retrieve client settings for a human task instance.
	 * A human task's client settings will mainly consist of the URL to be used to invoke the
     * coach associated with the task.
     *
	 * @param tkiid The id of the task instance.
	 * @param isRelativeUrl If true, the URL returned is a relative URL.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds a URL to be used to invoke
     *      the coach associated with the task, wrapped by {@link ru.bpmink.bpm.model.task.TaskClientSettings}
	 * @throws IllegalArgumentException if tkiid or isRelativeUrl is null
	 */
	RestRootEntity<TaskClientSettings> getTaskClientSettings(@Nonnull String tkiid, @Nonnull Boolean isRelativeUrl);

    /**
     * Retrieve available actions for human task instances.
     *
     * @param tkiids A list of IDs of human tasks (tkiid list) for which available actions should be returned.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds an information about available
     *      actions for specified tkiids.
     * @throws IllegalArgumentException if tkiids is null or empty.
     */
	RestRootEntity<TaskActions> getAvailableActions(@Nonnull List<String> tkiids);

    /**
     * Same as {@link #getAvailableActions(List)}.
     * By default it's holds {@code getAvailableActions(Collections.singletonList(tkiid))} logic.
     *
     * @throws IllegalArgumentException if tkiid is null.
     */
    RestRootEntity<TaskActions> getAvailableActions(@Nonnull String tkiid);

}
