package ru.bpmink.bpm.api.impl.simple;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import ru.bpmink.bpm.api.client.TaskClient;
import ru.bpmink.bpm.model.common.RestEntity;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.task.*;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.Date;
import java.util.Map;

@Immutable
public class TaskClientImpl extends BaseClient implements TaskClient {

	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	//Request parameters constants
	private static final String ACTION = "action";
	private static final String PARAMS = "params";
    private static final String DUE_DATE = "dueDate";
    private static final String PRIORITY = "priority";
	private static final String RELATIVE_URL = "relativeURL";
	private static final String SETTINGS_TYPE = "IBM_WLE_Coach";

    //Methods for tasks
	private static final String ACTION_ASSIGN = "assign";
	private static final String ACTION_COMPLETE = "finish";
	private static final String ACTION_CANCEL = "cancel";
	private static final String ACTION_START = "start";
	private static final String ACTION_GET_DATA = "getData";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_SETTINGS = "clientSettings";

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

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> getTask(@Nonnull String tkiid) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).build();

		return makeGet(httpClient, httpContext, uri, new TypeToken<RestRootEntity<TaskDetails>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
	public RestRootEntity<TaskStartData> startTask(@Nonnull String tkiid) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_START).build();

		return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<TaskStartData>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> assignTaskToMe(@Nonnull String tkiid) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
		
		Map<String, Object> query = Maps.newHashMap();
		query.put(ASSIGN_TO_ME, true);
		return assignTask(tkiid, query);
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> assignTaskBack(@Nonnull String tkiid) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		Map<String, Object> query = Maps.newHashMap();
		query.put(ASSIGN_BACK, true);
		return assignTask(tkiid, query);
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> assignTaskToUser(@Nonnull String tkiid, String userName) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		Map<String, Object> query = Maps.newHashMap();
		if (userName != null) {
			query.put(ASSIGN_TO_USER, userName);
		} else {
			query.put(ASSIGN_BACK, true);
		}
		return assignTask(tkiid, query);
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> assignTaskToGroup(@Nonnull String tkiid, String groupName) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		Map<String, Object> query = Maps.newHashMap();
		if (groupName != null) {
			query.put(ASSIGN_TO_GROUP, groupName);
		} else {
			query.put(ASSIGN_BACK, true);
		}
		return assignTask(tkiid, query);
	}
	
	private RestRootEntity<TaskDetails> assignTask(String tkiid, Map<String, Object> query) {
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_ASSIGN);

		if (query.entrySet().iterator().hasNext()) {
			Map.Entry<String, Object> entry = query.entrySet().iterator().next();
			uri.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
		}

		return makePost(httpClient, httpContext, uri.build(), new TypeToken<RestRootEntity<TaskDetails>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskDetails> completeTask(@Nonnull String tkiid, Map<String, Object> input) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_COMPLETE);
		if (input != null && input.size() > 0) {
			uri.addParameter(PARAMS, gson.toJson(input));
		}

		return makePost(httpClient, httpContext, uri.build(), new TypeToken<RestRootEntity<TaskDetails>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<RestEntity> cancelTask(@Nonnull String tkiid) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");

		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_CANCEL).build();

		return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<RestEntity>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
    public RestRootEntity<TaskDetails> updateTaskDueTime(@Nonnull String tkiid, @Nonnull Date dueTime) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
        dueTime =  Args.notNull(dueTime, "Task dueTime");

        URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_UPDATE).addParameter(DUE_DATE, dueTime).build();

		return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<TaskDetails>>() {});
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public RestRootEntity<TaskDetails> updateTaskPriority(@Nonnull String tkiid, @Nonnull TaskPriority priority) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
        priority = Args.notNull(priority, "Task priority");

        URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_UPDATE).addParameter(PRIORITY, priority).build();

		return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<TaskDetails>>() {});
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
	public RestRootEntity<TaskData> getTaskData(@Nonnull String tkiid, String fields) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(tkiid).addParameter(ACTION, ACTION_GET_DATA);

        if (fields != null) {
			uri.addParameter("fields", fields);
		}

		return makeGet(httpClient, httpContext, uri.build(), new TypeToken<RestRootEntity<TaskData>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
	@Override
	public RestRootEntity<TaskClientSettings> getTaskClientSettings(@Nonnull String tkiid, @Nonnull Boolean isRelativeUrl) {
		tkiid = Args.notNull(tkiid, "Task id (tkiid)");
		isRelativeUrl = Args.notNull(isRelativeUrl, "IsRelativeURL");

		URI uri = new SafeUriBuilder(rootUri).addPath(tkiid).addPath(ACTION_SETTINGS).addPath(SETTINGS_TYPE).addParameter(RELATIVE_URL, isRelativeUrl).build();
		return makeGet(httpClient, httpContext, uri, new TypeToken<RestRootEntity<TaskClientSettings>>() {});
	}
}

























