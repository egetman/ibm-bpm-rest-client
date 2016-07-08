package ru.bpmink.bpm.api.client;

import com.google.common.io.ByteSource;
import com.google.common.io.Closeables;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.bpmink.bpm.api.impl.BpmClientFactory;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.other.exposed.Item;
import ru.bpmink.bpm.model.process.ProcessDetails;
import ru.bpmink.bpm.model.task.TaskData;
import ru.bpmink.bpm.model.task.TaskDetails;
import ru.bpmink.bpm.model.task.TaskStartData;
import ru.bpmink.bpm.model.task.TaskState;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class TaskClientTest {

    private BpmClient bpmClient;
    private Logger logger = LoggerFactory.getLogger(getClass());
    //Exposed metadata for 'Standard HR Open New Position' business process definition.
    private Item hiringSampleMetadata;

    @BeforeClass
    public void prepareData() {
        initializeClient();
        fetchExposedProcess();

    }

    private void fetchExposedProcess() {
        //Default sample app 'Hiring Sample' have bpd(Process) with name 'Standard HR Open New Position'
        //We need to fetch it before all tests for reuse it's id's and preventing additional rest call for each test.
        hiringSampleMetadata = bpmClient.getExposedClient().getItemByName("Standard HR Open New Position");
    }

    private ProcessDetails getHiringSampleProcessInstance() {
        return bpmClient.getProcessClient()
                .startProcess(hiringSampleMetadata.getItemId(),
                        hiringSampleMetadata.getProcessAppId(),
                        null,
                        null,
                        null)
                .getPayload();
    }

    private void initializeClient() {
        final URL url = Resources.getResource("server.properties");
        final ByteSource byteSource = Resources.asByteSource(url);
        final Properties properties = new Properties();

        InputStream inputStream = null;
        try {
            inputStream = byteSource.openBufferedStream();
            properties.load(inputStream);
            bpmClient = BpmClientFactory.createClient(properties.getProperty("secure.url"),
                    properties.getProperty("default.user"),
                    properties.getProperty("default.password"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(inputStream);
        }
    }

    @AfterClass
    public void closeClient() throws IOException {
        Closeables.close(bpmClient, true);
    }

    @Test
    public void taskDetailsFetch() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();

        RestRootEntity<TaskDetails> taskDetails = bpmClient.getTaskClient().getTask(submitJobRequisition.getTkiid());
        logger.info(taskDetails.describe());
        Assert.assertNotNull(taskDetails, "Task details could not be null");
        Assert.assertEquals(taskDetails.getPayload().getTkiid(), submitJobRequisition.getTkiid(), "Tkiid must match");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskDetailsFetchThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().getTask(null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test
    public void taskDataFetch() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();

        RestRootEntity<TaskData> taskData = bpmClient.getTaskClient().getTaskData(submitJobRequisition.getTkiid(),
                                                                                    null);
        logger.info(taskData.describe());
        Assert.assertNotNull(taskData, "Task data could not be null");
        Assert.assertFalse(taskData.getPayload().getData().isEmpty(), "Default parameters should be present");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskDataFetchThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().getTaskData(null, null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test
    public void taskComplete() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();
        Assert.assertEquals(submitJobRequisition.getState(), TaskState.STATE_CLAIMED, "Task should be claimed");

        TaskDetails completedTask = bpmClient.getTaskClient().completeTask(submitJobRequisition.getTkiid(), null)
                .getPayload();

        logger.info(completedTask.describe());
        Assert.assertEquals(completedTask.getTkiid(), submitJobRequisition.getTkiid(), "Tkiid should match");
        Assert.assertEquals(completedTask.getState(), TaskState.STATE_FINISHED, "Task should be finished");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskCompleteThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().completeTask(null, null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    


}