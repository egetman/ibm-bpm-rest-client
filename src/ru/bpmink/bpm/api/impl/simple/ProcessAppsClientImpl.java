package ru.bpmink.bpm.api.impl.simple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import ru.bpmink.bpm.api.client.ProcessAppsClient;
import ru.bpmink.bpm.model.other.processapp.ProcessApps;

import java.io.IOException;
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

	
	@Override
	public ProcessApps listProcessApps() {
		HttpGet request = new HttpGet(rootUri);
		setRequestTimeOut(request, DEFAULT_TIMEOUT);
		setHeadersGet(request);
		logRequest(request, null);
		
		HttpResponse response;
		String body;
		try {
			response = httpContext == null ? httpClient.execute(request) : httpClient.execute(request, httpContext);
			body = IOUtils.toString(response.getEntity().getContent(), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't get ProcessApps object from Server with uri: " + rootUri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		return gson.fromJson(body, ProcessApps.class);
	}

}
