package ru.bpmink.bpm.api.impl.simple;

import com.google.gson.reflect.TypeToken;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import ru.bpmink.bpm.api.client.ProcessAppsClient;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.other.processapp.ProcessApps;

import java.net.URI;

@Immutable
public class ProcessAppsClientImpl extends BaseClient implements ProcessAppsClient {
	
	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	ProcessAppsClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
		this.httpClient = httpClient;
		this.rootUri = rootUri;
		this.httpContext = httpContext;
	}
	
	ProcessAppsClientImpl(URI rootUri, HttpClient httpClient) {
		this(rootUri, httpClient, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RestRootEntity<ProcessApps> listProcessApps() {
		return makeGet(httpClient, httpContext, rootUri, new TypeToken<RestRootEntity<ProcessApps>>() {});
	}

}
