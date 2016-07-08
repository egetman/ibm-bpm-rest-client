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
import ru.bpmink.bpm.model.process.ProcessState;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ProcessClientTest {

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
    public void processStart() {
        /*
            So, for processes:
            BpdId is exposed item itemId (item.getItemId())
            ProcessAppId is exposed item processAppId (item.getProcessAppId())
            SnapshotId is exposed item snapshotId (item.getSnapshotId())
            BranchId is exposed item branchId (item.getBranchId())
        */

        RestRootEntity<ProcessDetails> startedProcessInstance = bpmClient.getProcessClient().startProcess(
                hiringSampleMetadata.getItemId(), hiringSampleMetadata.getProcessAppId(), null, null, null);
        logger.info(startedProcessInstance.describe());

        Assert.assertNotNull(startedProcessInstance.getPayload(), "Process payload should be filled");
        Assert.assertEquals(startedProcessInstance.getPayload().getState(), ProcessState.STATE_RUNNING,
                "Process state should be: " + ProcessState.STATE_RUNNING);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void illegalArgumentExceptionOnStartProcessWithNullBpdId() {
        bpmClient.getProcessClient().startProcess(null, hiringSampleMetadata.getProcessAppId(), null, null, null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void illegalArgumentExceptionOnStartProcessWithNullOptionalIds() {
        bpmClient.getProcessClient().startProcess(hiringSampleMetadata.getItemId(), null, null, null, null);
        Assert.assertTrue(false, "Exception must be thrown before this assert");
    }


}
