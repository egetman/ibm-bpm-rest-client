package ru.bpmink.bpm.api.impl.simple;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
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

	protected String makeGet(HttpClient httpClient, HttpContext httpContext, URI endpoint) {
		try(ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
			HttpGet request = new HttpGet(endpoint);
			setRequestTimeOut(request, DEFAULT_TIMEOUT);
			setHeadersGet(request);

			logRequest(request, null);

			HttpResponse response = httpClient.execute(request, httpContext);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = response.getEntity().getContent().read(buffer)) != -1) {
				arrayOutputStream.write(buffer, 0, length);
			}
			String body = arrayOutputStream.toString("UTF-8");

			logResponse(response, body);
			request.releaseConnection();

			return body;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't get Entity object from Server with uri: " + endpoint, e);
		}
	}

	//For avoid null checks
	protected <T> T nonNull(T obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
		return obj;
	}

	
}
