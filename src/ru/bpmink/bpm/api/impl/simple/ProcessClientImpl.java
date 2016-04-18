package ru.bpmink.bpm.api.impl.simple;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import ru.bpmink.bpm.api.client.ProcessClient;
import ru.bpmink.bpm.model.process.ProcessDetails;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Map;

@Immutable
class ProcessClientImpl extends BaseClient implements ProcessClient {

	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	//Request parameters constants
	private static final String ACTION = "action";
	private static final String PROCESS_DEFINITION_ID = "bpdId";
	private static final String SNAPSHOT_ID = "snapshotId";
	private static final String BRANCH_ID = "branchId";
	private static final String PROCESS_APP_ID = "processAppId";
	private static final String PARAMS = "params";
	
	//Methods for processes
	private static final String ACTION_START = "start";
	private static final String ACTION_SUSPEND = "suspend";
	private static final String ACTION_RESUME = "resume";
	private static final String ACTION_TERMINATE = "terminate";

	
	ProcessClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
		this.httpClient = httpClient;
		this.rootUri = rootUri;
		this.httpContext = httpContext;
	}
	
	ProcessClientImpl(URI rootUri, HttpClient httpClient) {
		this(rootUri, httpClient, null);
	}

	
	//Will use only one parameter of processAppId, snapshotId or branchId. Which one is not specified.
	@Override
	public ProcessDetails startProcess(@Nonnull String bpdId, String processAppId, String snapshotId, String branchId, Map<String, Object> input) {
		bpdId = Args.notNull(bpdId, "BusinessProcessDefinition (bpdId)");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		Map<String, String> choice = Maps.newHashMap();
		choice.put(SNAPSHOT_ID, snapshotId);
		choice.put(BRANCH_ID, branchId);
		choice.put(PROCESS_APP_ID, processAppId);
		Map.Entry<String, String> entry = reduce(choice);
			
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addParameter(ACTION, ACTION_START).addParameter(PROCESS_DEFINITION_ID, bpdId).addParameter(entry.getKey(), entry.getValue());
		
		if (input != null && input.size() > 0) {
			uri.addParameter(PARAMS, gson.toJson(input));
		}
		
		String body = makePost(httpClient, httpContext, uri.build());
		return gson.fromJson(body, ProcessDetails.class);
	}
	
	private Map.Entry<String, String> reduce(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				return entry;
			}
		}
		throw new IllegalArgumentException("One of processAppId, snapshotId or branchId must be specified");
	}

	@Override
	public ProcessDetails suspendProcess(@Nonnull String piid) {
		return changeProcessState(piid, ACTION_SUSPEND);
	}

	@Override
	public ProcessDetails resumeProcess(@Nonnull String piid) {
		return changeProcessState(piid, ACTION_RESUME);
	}

	@Override
	public ProcessDetails terminateProcess(@Nonnull String piid) {
		return changeProcessState(piid, ACTION_TERMINATE);
	}
	
	private ProcessDetails changeProcessState(String piid,  String action) {
		piid = Args.notNull(piid, "ProcessInstanceID (piid)");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();

		URI uri = new SafeUriBuilder(rootUri).addPath(piid).addParameter(ACTION, action).build();
	
		String body = makePost(httpClient, httpContext, uri);
		return gson.fromJson(body, ProcessDetails.class);
	}

	@Override
	public ProcessDetails currentState(@Nonnull String piid) {
		piid = Args.notNull(piid, "ProcessInstanceID (piid)");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();

		URI uri = new SafeUriBuilder(rootUri).addPath(piid).build();

		String body = makeGet(httpClient, httpContext, uri);
		return gson.fromJson(body, ProcessDetails.class);
	}
	
}
