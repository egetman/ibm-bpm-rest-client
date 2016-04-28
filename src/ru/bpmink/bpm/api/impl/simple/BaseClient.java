package ru.bpmink.bpm.api.impl.simple;

import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bpmink.bpm.model.common.Describable;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.util.Utils;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

@Immutable
abstract class BaseClient {
	
	private static Logger logger = LoggerFactory.getLogger(BaseClient.class.getName());
	
	private static final RequestConfig DEFAULT_CONFIG = RequestConfig.custom()
			.setCookieSpec(CookieSpecs.DEFAULT)
			.setTargetPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
			.setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC)).build();
	
	private static final String JSON_CONTENT_TYPE = "application/json";
	private static final String FORM_URL_CONTENT_TYPE = "application/x-www-form-urlencoded";
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	protected static final String DEFAULT_SEPARATOR = ",";
	protected static final int DEFAULT_TIMEOUT = 120000; // 120 seconds 
	
	protected void setRequestTimeOut(HttpRequestBase request, int timeOut) {
		RequestConfig requestConfig = RequestConfig.copy(DEFAULT_CONFIG).setSocketTimeout(timeOut).setConnectTimeout(timeOut).setConnectionRequestTimeout(timeOut).build();
		request.setConfig(requestConfig);
	}
	
	protected void setHeadersGet(HttpRequestBase request) {
		request.addHeader(HttpHeaders.CONTENT_TYPE, JSON_CONTENT_TYPE);
		request.setHeader(HttpHeaders.ACCEPT, JSON_CONTENT_TYPE);
	}
	
	protected void setHeadersPut(HttpRequestBase request) {
		setHeadersGet(request); // The same one
	}

	
	protected void setHeadersPost(HttpRequestBase request) {
		request.addHeader(HttpHeaders.CONTENT_TYPE, FORM_URL_CONTENT_TYPE);
		request.setHeader(HttpHeaders.ACCEPT, JSON_CONTENT_TYPE);
	}

	protected void logRequest(HttpRequest request, String body) {
		logger.info("Prepared Request for uri: " + request.getRequestLine().getUri());
		logger.info("HTTP Request headers: " + Arrays.toString(request.getAllHeaders()));
		if (logger.isDebugEnabled()) {
			logger.debug("Request body: " + body);
		}
	}
	
	protected void logResponse(HttpResponse response, String body) {
		logger.info("HTTP Response had a " + response.getStatusLine().getStatusCode() + " status code.");
		logger.info("Reason: " + response.getStatusLine().getReasonPhrase());
		if (logger.isDebugEnabled()) {
			logger.debug("Response headers: " + Arrays.toString(response.getAllHeaders()));
			logger.debug("Response: " + response);
			logger.debug("Response body: " + body);
		}
	}

	protected <T extends Describable> RestRootEntity<T> makeGet(HttpClient httpClient, HttpContext httpContext, URI endpoint, TypeToken<RestRootEntity<T>> typeToken) {
		try {
			HttpGet request = new HttpGet(endpoint);
			setRequestTimeOut(request, DEFAULT_TIMEOUT);
			setHeadersGet(request);

			logRequest(request, null);

			HttpResponse response = httpClient.execute(request, httpContext);
			RestRootEntity<T> entity = makeEntity(response, typeToken);

			request.releaseConnection();

			return entity;
		} catch (IOException e) {
			logger.error("Can't get Entity object from Server with uri: " + endpoint, e);
			e.printStackTrace();
			throw new RuntimeException("Can't get Entity object from Server with uri: " + endpoint, e);
		}
	}

	protected <T extends Describable> RestRootEntity<T> makePost(HttpClient httpClient, HttpContext httpContext, URI endpoint, TypeToken<RestRootEntity<T>> typeToken) {
		try {
			HttpPost request = new HttpPost(endpoint);
			setRequestTimeOut(request, DEFAULT_TIMEOUT);
			setHeadersPost(request);

			logRequest(request, null);

			HttpResponse response = httpClient.execute(request, httpContext);
			RestRootEntity<T> entity = makeEntity(response, typeToken);

			request.releaseConnection();

			return entity;
		} catch (IOException e) {
			logger.error("Can't update Entity object from Server with uri " + endpoint, e);
			e.printStackTrace();
			throw new RuntimeException("Can't update Entity object from Server with uri " + endpoint, e);
		}
	}

	private  <T extends Describable> RestRootEntity<T> makeEntity(HttpResponse response, TypeToken<RestRootEntity<T>> typeToken) {
		try {
			Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
			String body = Utils.inputStreamToString(response.getEntity().getContent());
			logResponse(response, body);

			RestRootEntity<T> entity = gson.fromJson(body, typeToken.getType());
			//In case of system / communication errors body will be empty. (i.e. if provided credentials was wrong
			//we will receive 401 code - Unauthorized. So we set correct status code in to the entity, and it's payload will be empty)
			entity = MoreObjects.firstNonNull(entity, new RestRootEntity<T>());

			//In error case status field updated by 'error' value. So just replace it by actual status code.
			entity.setStatus(String.valueOf(response.getStatusLine().getStatusCode()));
			return entity;
		} catch (IOException e) {
			logger.error("Can't create response Entity object with type: " + typeToken.getType(), e);
			e.printStackTrace();
			throw new RuntimeException("Can't create response Entity object with type: " + typeToken.getType(), e);
		}
	}

}
