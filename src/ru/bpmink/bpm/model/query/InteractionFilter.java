package ru.bpmink.bpm.model.query;

/**
 * Name of a predefined user interaction
 */
public enum InteractionFilter {

	/**
	 * Work on tasks. This implies the retrieval of tasks which are already claimed by the current user. 
	 * Tasks contained in suspended process instances are excluded.
	 */
	WORK_ON,
	
	/**
	 * Work on tasks, same as WORK_ON.
	 */
	WORK_ON_ACTIVE,
	
	/**
	 * Assess available tasks. This implies the retrieval of tasks which can be claimed by the current user. 
	 * Tasks contained in suspended process instances are excluded.
	 */
	ASSESS_AVAILABLE,
	
	/**
	 * Assess and work on tasks. This implies the retrieval of tasks as implied by the WORK_ON or the ASSESS_AVAILABLE filter values.
	 */
	ASSESS_AND_WORK_ON,
	
	/**
	 * Work on tasks. This implies the retrieval of completed tasks which are claimed by the current user.
	 */
	CHECK_COMPLETED,
	
	/**
	 * Browse all tasks. This implies the retrieval of to-do, collaboration and invocation tasks for which the current user is allowed to see.
	 */
	BROWSE_ALL
	
}
