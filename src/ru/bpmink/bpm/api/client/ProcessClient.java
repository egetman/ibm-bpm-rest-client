package ru.bpmink.bpm.api.client;


import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.process.ProcessDetails;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;


//TODO: Add full api possibilities
/**
 * Client for process api actions.
 */
public interface ProcessClient {

    /**
     * Start a new instance of a process. One of snapshotId, branchId, or processAppId must be specified.
     * Will use only one parameter of processId, snapshotId or branchId. Which one is not specified. Set another params
     * to null for exact behavior.
     *
     * @param bpdId The id of the Business Process Definition to be used.
     * @param processAppId The id of the process application containing the Business Process Definition. If this
     *      parameter is specified, then the tip snapshot of the default branch within the specified
     *      process application will be used.
     * @param snapshotId The id of the snapshot containing the Business Process Definition.
     * @param branchId The id of the branch containing the Business Process Definition. If this parameter is specified,
     *      then the tip snapshot of the specified branch will be used.
     * @param input Input parameters of the process. format: input parameter name + input parameter value.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains detailed process information
     *      as generic type: {@link ru.bpmink.bpm.model.process.ProcessDetails}
     * @throws IllegalArgumentException if bpdId is null or if all of processAppId, snapshotId and branchId are null's
     */
    RestRootEntity<ProcessDetails> startProcess(@Nonnull String bpdId,
                                                @Nullable String processAppId,
                                                @Nullable String snapshotId,
                                                @Nullable String branchId,
                                                @Nullable Map<String, Object> input);

    /**
     * Suspend a process instance.
     *
     * @param piid The id of the process instance to be suspended.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains detailed process information
     *     	as generic type: {@link ru.bpmink.bpm.model.process.ProcessDetails}
     * @throws IllegalArgumentException if processId is null
     */
    RestRootEntity<ProcessDetails> suspendProcess(@Nonnull String piid);

    /**
     * Resume a process instance.
     *
     * @param piid The id of the process instance to be resumed.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains detailed process information
     *     	as generic type: {@link ru.bpmink.bpm.model.process.ProcessDetails}
     * @throws IllegalArgumentException if processId is null
     */
    RestRootEntity<ProcessDetails> resumeProcess(@Nonnull String piid);

    /**
     * Terminate a process instance.
     *
     * @param piid The id of the process instance to be terminated.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains detailed process information
     *     	as generic type: {@link ru.bpmink.bpm.model.process.ProcessDetails}
     * @throws IllegalArgumentException if processId is null
     */
    RestRootEntity<ProcessDetails> terminateProcess(@Nonnull String piid);


    /**
     * Retrieves details of a process instance.
     *
     * @param piid The id of the process instance to be retrieved.
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains detailed process information
     *     	as generic type: {@link ru.bpmink.bpm.model.process.ProcessDetails}
     * @throws IllegalArgumentException if processId is null
     */
    RestRootEntity<ProcessDetails> currentState(@Nonnull String piid);

}
