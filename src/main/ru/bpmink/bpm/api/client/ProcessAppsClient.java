package ru.bpmink.bpm.api.client;


import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.other.processapp.ProcessApps;

/**
 * Client for process apps api actions.
 */
public interface ProcessAppsClient {

    /**
     * Retrieves all process applications that are installed in the system.
     *
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains information about all
     *      installed process applications: {@link ru.bpmink.bpm.model.other.processapp.ProcessApps}
     */
    RestRootEntity<ProcessApps> listProcessApps();

}
