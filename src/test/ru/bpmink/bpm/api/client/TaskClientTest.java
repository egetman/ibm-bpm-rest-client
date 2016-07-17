package ru.bpmink.bpm.api.client;

import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.google.common.io.Closeables;
import com.google.common.io.Resources;
import com.google.gson.Gson;
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
import ru.bpmink.bpm.model.task.TaskActions;
import ru.bpmink.bpm.model.service.ServiceData;
import ru.bpmink.bpm.model.task.TaskDetails;
import ru.bpmink.bpm.model.task.TaskState;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
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

        RestRootEntity<ServiceData> taskData = bpmClient.getTaskClient().getTaskData(submitJobRequisition.getTkiid(),
                                                                                    "instanceId", "currentPosition");
        logger.info(taskData.describe());
        Assert.assertNotNull(taskData, "Task data could not be null");
        Assert.assertFalse(taskData.getPayload().getVariables().isEmpty(), "Default parameters should be present");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskDataFetchThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().getTaskData(null, "instanceId", "currentPosition");
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test
    public void taskComplete() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();
        Assert.assertEquals(submitJobRequisition.getState(), TaskState.STATE_CLAIMED, "Task should be claimed");

        Position position = new Position();
        //No need to approve old position type
        position.setPositionType("Existing");

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("currentPosition", position);

        TaskDetails completedTask = bpmClient.getTaskClient().completeTask(submitJobRequisition.getTkiid(), parameters)
                .getPayload();

        logger.info(completedTask.describe());
        Assert.assertEquals(completedTask.getTkiid(), submitJobRequisition.getTkiid(), "Tkiid should match");
        Assert.assertEquals(completedTask.getState(), TaskState.STATE_FINISHED, "Task should be finished");

        //Now check, that enclosing process instance received submitted data
        processDetails = bpmClient.getProcessClient().currentState(processDetails.getPiid()).getPayload();
        logger.info(processDetails.describe());

        Map<String, Object> variables = processDetails.getVariables();
        Assert.assertTrue(variables.containsKey("currentPosition"));

        position = new Gson().fromJson(String.valueOf(variables.get("currentPosition")), Position.class);
        Assert.assertEquals(position.getPositionType(), "Existing");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskCompleteThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().completeTask(null, null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test
    public void taskAvailableActions() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();

        TaskActions taskActions = bpmClient.getTaskClient().getAvailableActions(submitJobRequisition.getTkiid())
                .getPayload();

        logger.info(taskActions.describe());
        Assert.assertEquals(taskActions.getTaskActions().size(), 1, "Available actions was submitted for 1 task");
        Assert.assertFalse(taskActions.getTaskActions().iterator().next().getActions().isEmpty(), "Actions should " +
                "present for claimed task");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskAvailableActionsThrowExceptionWhenNullTkiid() {
        bpmClient.getTaskClient().getAvailableActions((String) null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void taskAvailableActionsThrowExceptionWhenEmptyTkiids() {
        bpmClient.getTaskClient().getAvailableActions(Collections.<String>emptyList());
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test
    public void taskSetData() {
        ProcessDetails processDetails = getHiringSampleProcessInstance();
        Assert.assertFalse(processDetails.getTasks().isEmpty(), "Test could not be run, if there is no tasks");
        TaskDetails submitJobRequisition = processDetails.getTasks().iterator().next();
        logger.info(submitJobRequisition.describe());

        String tkiid = submitJobRequisition.getTkiid();

        Position position = new Position();
        //No need to approve old position type
        position.setPositionType("Existing");

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("currentPosition", position);

        RestRootEntity<ServiceData> taskData = bpmClient.getTaskClient().setTaskData(tkiid, parameters);
        logger.info(taskData.describe());

        //Now check that task received our position type
        TaskDetails updatedTask = bpmClient.getTaskClient().getTask(tkiid).getPayload();
        logger.info(updatedTask.describe());

        Map<String, Object> variables = updatedTask.getInstanceData().getVariables();
        Assert.assertTrue(variables.containsKey("currentPosition"));

        position = new Gson().fromJson(String.valueOf(variables.get("currentPosition")), Position.class);
        Assert.assertEquals(position.getPositionType(), "Existing");
    }

    //Variable for Submit Requisition task
    private class Position {

        private String positionType;
        private Person replacement;
        private String jobTitle;
        private String iId;

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public Person getReplacement() {
            return replacement;
        }

        public void setReplacement(Person replacement) {
            this.replacement = replacement;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getiId() {
            return iId;
        }

        public void setiId(String iId) {
            this.iId = iId;
        }
    }

    private class Person {

        private String lastName;
        private String firstName;
        private String supervisor;
        private Date startDate;
        private String payLevel;
        private String payType;
        private String notes;

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public String getPayLevel() {
            return payLevel;
        }

        public void setPayLevel(String payLevel) {
            this.payLevel = payLevel;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}