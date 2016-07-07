package ru.bpmink.bpm.api.client;

import java.io.Closeable;


/**
 * Public root api client.
 * You can obtain different api client's through it.
 * {@link ru.bpmink.bpm.api.client.BpmClient} propagates all it's settings to another client's.
 */
public interface BpmClient extends Closeable {

	/**
	 * Client for actions on exposed bpm api.
	 * @return {@link ru.bpmink.bpm.api.client.ExposedClient}
	 */
	ExposedClient getExposedClient();
	
	/**
	 * Client for actions on process bpm api.
	 * @return {@link ru.bpmink.bpm.api.client.ProcessClient}
	 */
	ProcessClient getProcessClient();
	
	/**
	 * Client for actions on task bpm api.
	 * @return {@link ru.bpmink.bpm.api.client.TaskClient}
	 */
	TaskClient getTaskClient();
	
	/**
	 * Client for actions on process apps bpm api.
	 * @return {@link ru.bpmink.bpm.api.client.ProcessAppsClient}
	 */
	ProcessAppsClient getProcessAppsClient();

	/**
	 * Client for actions on task query bpm api;
	 * @return {@link ru.bpmink.bpm.api.client.QueryClient}
	 */
	QueryClient getTaskQueryClient();
	
	/**
	 * Client for actions on task template query bpm api;
	 * @return {@link ru.bpmink.bpm.api.client.QueryClient}
	 */
	QueryClient getTaskTemplateQueryClient();
	
	/**
	 * Client for actions on process query bpm api;
	 * @return {@link ru.bpmink.bpm.api.client.QueryClient}
	 */
	QueryClient getProcessQueryClient();
}
