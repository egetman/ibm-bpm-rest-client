package ru.bpmink.bpm.api.client;

import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.service.ServiceData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Client for service api actions.
 */
public interface ServiceClient {

    /**
     * Get data from specified running service.
     *
     * @param instanceId The "instanceId" parameter is either a service instance id (@numeric) or
     *      a task instance id (@numeric).
     * @param fields Comma-separated list of fields.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds task data information:
     *      {@link ServiceData}
     * @throws IllegalArgumentException if instanceId is null
     */
    RestRootEntity<ServiceData> getTaskData(@Nonnull String instanceId, @Nullable String fields);

    /**
     * This method sets one or more variables within a running service.
     *
     * @param instanceId The "instanceId" parameter is either a service instance id (@numeric) or
     *      a task instance id (@numeric).
     * @param parameters A {@link Map} that contains one or more variable settings.
     *      Each of the variables will be set in the context of the running task.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds task data information:
     *      {@link ServiceData}
     * @throws IllegalArgumentException if instanceId or jsonParameters is null.
     */
    RestRootEntity<ServiceData> setTaskData(@Nonnull String instanceId, @Nonnull Map<String, Object> parameters);

    /**
     * This method sets one variable within a running service.
     *
     * @param instanceId The "instanceId" parameter is either a service instance id (@numeric) or
     *      a task instance id (@numeric).
     * @param field The name of the variable to be set.
     * @param value Object, that represents the value to be assigned to the variable.
     *      Note: Object will be converted to json for interacting with task.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, which holds task data information:
     *      {@link ServiceData}
     * @throws IllegalArgumentException if instanceId, field or value is null.
     */
    RestRootEntity<ServiceData> setTaskData(@Nonnull String instanceId, @Nonnull String field, @Nonnull Object value);

}
