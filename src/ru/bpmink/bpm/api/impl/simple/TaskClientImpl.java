package ru.bpmink.bpm.api.impl.simple;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HttpContext;
import ru.bpmink.bpm.api.client.TaskClient;
import ru.bpmink.bpm.model.task.TaskData;
import ru.bpmink.bpm.model.task.TaskDetails;
import ru.bpmink.bpm.model.task.TaskStartData;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

@Immutable
public class TaskClientImpl extends BaseClient implements TaskClient {

	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	//Request parameters constants
	private static final String ACTION = "action";
	private static final String PARAMS = "params";
	
	//Methods for tasks
	private static final String ACTION_ASSIGN = "assign";
	private static final String ACTION_COMPLETE = "finish";
	private static final String ACTION_START = "start";
	private static final String ACTION_GET_DATA = "getData";
	@SuppressWarnings("unused")
	private static final String ACTION_SET_DATA = "setData";
	
	//Assign constants
	private static final String ASSIGN_BACK = "back";
	private static final String ASSIGN_TO_ME = "toMe";
	private static final String ASSIGN_TO_USER = "toUser";
	private static final String ASSIGN_TO_GROUP = "toGroup";
	
	TaskClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
		this.httpClient = httpClient;
		this.rootUri = rootUri;
		this.httpContext = httpContext;
	}
	
	TaskClientImpl(URI rootUri, HttpClient httpClient) {
		this(rootUri, httpClient, null);
	}

	@Override
	public TaskDetails getTask(@Nonnull String tkiid) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).build();
		
		HttpGet request = new HttpGet(uri);
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersGet(request);
	
		logRequest(request, null);
		
		String body;
		HttpResponse response;
		
		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't get TaskDetails object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, TaskDetails.class);
	}

	@Override
	public TaskStartData startTask(@Nonnull String tkiid) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_START).build();
		
		HttpPost request = new HttpPost(uri);
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersPost(request);
	
		logRequest(request, null);
		
		String body;
		HttpResponse response;
		
		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't get TaskStartData object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, TaskStartData.class);
	}
	
	@Override
	public TaskDetails assignTaskToMe(@Nonnull String tkiid) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");
		
		Map<String, Object> query = Maps.newHashMap();
		query.put(ASSIGN_TO_ME, true);
		return assignTask(tkiid, query);
	}

	@Override
	public TaskDetails assignTaskBack(@Nonnull String tkiid) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");

		Map<String, Object> query = Maps.newHashMap();
		query.put(ASSIGN_BACK, true);
		return assignTask(tkiid, query);
	}

	@Override
	public TaskDetails assignTaskToUser(@Nonnull String tkiid, String userName) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");

		Map<String, Object> query = Maps.newHashMap();
		if (userName != null) {
			query.put(ASSIGN_TO_USER, userName);
		} else {
			query.put(ASSIGN_BACK, true);
		}
		return assignTask(tkiid, query);
	}

	@Override
	public TaskDetails assignTaskToGroup(@Nonnull String tkiid, String groupName) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");

		Map<String, Object> query = Maps.newHashMap();
		if (groupName != null) {
			query.put(ASSIGN_TO_GROUP, groupName);
		} else {
			query.put(ASSIGN_BACK, true);
		}
		return assignTask(tkiid, query);
	}
	
	private TaskDetails assignTask(String tkiid, Map<String, Object> query) {
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_ASSIGN);

		if (query.entrySet().iterator().hasNext()) {
			Map.Entry<String, Object> entry = query.entrySet().iterator().next();
			uri.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
		}

		HttpPost request = new HttpPost(uri.build());
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersPut(request);
	
		logRequest(request, null);
		
		String body;
		HttpResponse response;

		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't reassign task " + tkiid, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, TaskDetails.class);
	}

	@Override
	public TaskDetails completeTask(@Nonnull String tkiid, Map<String, Object> input) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_COMPLETE);
		if (input != null && input.size() > 0) {
			uri.addParameter(PARAMS, gson.toJson(input));
		}

		HttpPost request = new HttpPost(uri.build());
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersPost(request);
	
		logRequest(request, null);
		
		String body;
		HttpResponse response;
		
		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't complete task " + tkiid + " with input parameters: " + input, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, TaskDetails.class);
	}

	@Override
	public TaskData getData(@Nonnull String tkiid, String fields) {
		tkiid = nonNull(tkiid, "Task id (tkiid) can't be null!");
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_GET_DATA);
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		if (fields != null) {
			uri.addParameter("fields", fields);
		}

		HttpGet request = new HttpGet(uri.build());
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersGet(request);

		logRequest(request, null);

		String body;
		HttpResponse response;

		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't get TaskData object from Server with uri: " + uri, e);
		}

		logResponse(response, body);
		request.releaseConnection();

		return gson.fromJson(body, TaskData.class);

	}

}
