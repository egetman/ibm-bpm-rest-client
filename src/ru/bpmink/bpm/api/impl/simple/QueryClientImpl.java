package ru.bpmink.bpm.api.impl.simple;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import ru.bpmink.bpm.api.client.QueryClient;
import ru.bpmink.bpm.model.query.*;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class QueryClientImpl extends BaseClient implements QueryClient {
	
	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	//Endpoind additional paths
	private static final String QUERIES = "queries";
	private static final String QUERY = "query";
	
	//Request parameters constants
	private static final String PROCESS_APP_NAME = "processAppName";
	private static final String KIND = "kind";
	private static final String CONTENT = "content";
	private static final String COUNT = "count";
	private static final String ATTRIBUTES = "attributes";
	private static final String SELECTED_ATTRIBUTES = "selectedAttributes";
	private static final String SORT_ATTRIBUTES = "sortAttributes";
	private static final String INTERACTION_FILTER = "interactionFilter";
	private static final String FILTER_BY_CURRENT_USER = "filterByCurrentUser";
	private static final String SIZE = "size";

	QueryClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
		this.httpClient = httpClient;
		this.rootUri = rootUri;
		this.httpContext = httpContext;
	}
	
	QueryClientImpl(URI rootUri, HttpClient httpClient) {
		this(rootUri, httpClient, null);
	}
	@Override
	public QueryList listQueries() {
		return this.listQueries(null, null, null);
	} 

	@Override
	public QueryList listQueries(String processAppName, QueryKind kind, List<QueryAttribute> content) {

		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(QUERIES);
		
		if (processAppName != null) {
			uri.addParameter(PROCESS_APP_NAME, processAppName);
		}
		if (kind != null) {
			uri.addParameter(KIND, kind.name());
		}
		if (content != null) {
			uri.addParameter(CONTENT, Joiner.on(DEFAULT_SEPARATOR).skipNulls().join(Collections2.transform(content, new ContentFunction())));
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
			throw new RuntimeException("Can't get QueryList object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, QueryList.class);
	}
	
	@Override
	public QueryResultSet queryEntityList(@Nonnull Query query, List<QueryAttribute> selectedAttributes, InteractionFilter interactionFilter, String processAppName,	List<SortAttribute> sortAttributes, Integer size, Boolean filterByCurrentUser) {
		query = nonNull(query, "Search query can't be null!");
		String querySearch = nonNull(query.getName(), "Search query name can't be null!");
		
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(QUERY).addPath(querySearch);
		
		if (selectedAttributes != null) {
			uri.addParameter(SELECTED_ATTRIBUTES, Joiner.on(DEFAULT_SEPARATOR).skipNulls().join(Collections2.transform(selectedAttributes, new NameFunction())));
		}
		if (interactionFilter != null) {
			uri.addParameter(INTERACTION_FILTER, interactionFilter.name());
		}
		if (processAppName != null) {
			uri.addParameter(PROCESS_APP_NAME, processAppName);
		}
		if (sortAttributes != null) {
			uri.addParameter(SORT_ATTRIBUTES, Joiner.on(DEFAULT_SEPARATOR).skipNulls().join(Collections2.transform(sortAttributes, new SortFunction())));
		}
		if (size != null) {
			uri.addParameter(SIZE, size);
		}
		if (filterByCurrentUser != null) {
			uri.addParameter(FILTER_BY_CURRENT_USER, filterByCurrentUser);
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
			throw new RuntimeException("Can't get QueryResultSet object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, QueryResultSet.class);
	}
	
	@Override
	public QueryResultSetCount queryEntityListCount(@Nonnull Query query, InteractionFilter interactionFilter, String processAppName, Boolean filterByCurrentUser) {
		query = nonNull(query, "Search query can't be null!");
		String querySearch = nonNull(query.getName(), "Search query name can't be null!");

		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(QUERY).addPath(querySearch).addPath(COUNT);
		
		if (interactionFilter != null) {
			uri.addParameter(INTERACTION_FILTER, interactionFilter.name());
		}
		if (processAppName != null) {
			uri.addParameter(PROCESS_APP_NAME, processAppName);
		}
		if (filterByCurrentUser != null) {
			uri.addParameter(FILTER_BY_CURRENT_USER, filterByCurrentUser);
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
			throw new RuntimeException("Can't get QueryResultSetCount object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, QueryResultSetCount.class);
	}

	@Override
	public QueryAttributes queryAttributes(@Nonnull Query query, String processAppName) {
		query = nonNull(query, "Search query can't be null!");
		String querySearch = nonNull(query.getName(), "Search query name can't be null!");

		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(QUERY).addPath(querySearch).addPath(ATTRIBUTES);
		
		if (processAppName != null) {
			uri.addParameter(PROCESS_APP_NAME, processAppName);
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
			throw new RuntimeException("Can't get QueryAttributes object from Server with uri: " + uri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		return gson.fromJson(body, QueryAttributes.class);
	}

	
	private static class ContentFunction implements Function<QueryAttribute, String> {
		@Override
		public String apply(QueryAttribute input) {
			return input.getContent();
		}
	}
	
	private static class NameFunction implements Function<QueryAttribute, String> {
		@Override
		public String apply(QueryAttribute input) {
			return input.getName();
		}
	}
	
	private static class SortFunction implements Function<SortAttribute, String> {
		@Override
		public String apply(SortAttribute input) {
			return input.getName() + " " + input.getSortOrder().name();
		}
	}

}
