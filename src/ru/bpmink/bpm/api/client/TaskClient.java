package ru.bpmink.bpm.api.client;

import ru.bpmink.bpm.model.common.RestEmptyEntity;
import ru.bpmink.bpm.model.task.TaskData;
import ru.bpmink.bpm.model.task.TaskDetails;
import ru.bpmink.bpm.model.task.TaskPriority;
import ru.bpmink.bpm.model.task.TaskStartData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Map;


//TODO: Add full api possibilities
/**
* Client for task api actions.
*/
public interface TaskClient {

	/**
	 * Retrieves the details of a task.
	 * @param tkiid The id of the task instance to be retrieved.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails getTask(@Nonnull String tkiid);
	
	/**
	 * Use this method to start a task. The input variables defined on the task will be set according to the definitions in 
	 * the associated business process instance. The task will proceed until the first coach is encountered. 
	 * @param tkiid The id of the task instance to be started.
	 * @return task start data which contains a list of attribute/value pairs (see {@link ru.bpmink.bpm.model.task.TaskStartData}})
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskStartData startTask(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task to the current user. 
	 * @param tkiid The id of the task instance to be started.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails assignTaskToMe(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task back to the original task owner. 
	 * @param tkiid The id of the task instance to be assigned.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails assignTaskBack(@Nonnull String tkiid);
	
	/**
	 * Assign the specified task to another user. 
	 * If userName is null, reassigned task back to the original task owner. 
	 * @param tkiid The id of the task instance to be assigned.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails assignTaskToUser(@Nonnull String tkiid, @Nullable String userName);
	
	/**
	 * Assign the specified task to a group. 
	 * If groupName is null, reassigned task back to the original task owner. 
	 * @param tkiid The id of the task instance to be assigned.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails assignTaskToGroup(@Nonnull String tkiid, @Nullable String groupName);
	
	/**
	 * Finish the specified task.
	 * @param tkiid The id of the task instance to be finished.
	 * @param input Parameters to finish specified task/activity. Parameters NOT propagated to enclosing process. Use Task 
	 * 			api setData(@Nonnull String tkiid, @Nullable Map<String, Object> input) for that purpose.
	 * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskDetails completeTask(@Nonnull String tkiid, @Nullable Map<String, Object> input);

	/**
	 * Cancel the specified task.
	 * @param tkiid The id of the task instance to be cancelled.
	 * @return empty response (see {@link ru.bpmink.bpm.model.common.RestEmptyEntity}). If the call was unsuccessful, error
	 * 			details will be filled with information.
	 * @throws IllegalArgumentException if tkiid is null
	 */
	RestEmptyEntity cancelTask(@Nonnull String tkiid);

	/**
	 * Get data from specified task.
	 * @param tkiid The id of the task instance.
	 * @param fields Comma-separated list of fields.
	 * @return task data information (see {@link ru.bpmink.bpm.model.task.TaskData});
	 * @throws IllegalArgumentException if tkiid is null
	 */
	TaskData getTaskData(@Nonnull String tkiid, @Nullable String fields);

    /**
     * Update a task's priority.
     * @param tkiid The id of the task instance to be updated.
     * @param priority Is new task priority. (see {@link ru.bpmink.bpm.model.task.TaskPriority});
     * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
     * @throws IllegalArgumentException if priority is null
     */
    TaskDetails updateTaskPriority(@Nonnull String tkiid, @Nonnull TaskPriority priority);


    /**
     * Update a task's due time.
     * @param tkiid The id of the task instance to be updated.
     * @param dueTime Is new task due time.
     * @return the detailed task information (see {@link ru.bpmink.bpm.model.task.TaskDetails});
     * @throws IllegalArgumentException if dueTime is null
     */
    TaskDetails updateTaskDueTime(@Nonnull String tkiid, @Nonnull Date dueTime);

}
