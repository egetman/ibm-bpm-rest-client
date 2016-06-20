package ru.bpmink.util;

import com.google.common.base.MoreObjects;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.bpmink.util.Constants.*;

/**
 * Builder for {@link URI} instances.
 * Added some useful methods.
 * Doesn't throw checked exceptions.
 */
public class SafeUriBuilder extends URIBuilder {

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
     * Constructs an empty instance.
     */
	public SafeUriBuilder() {
		super();
	}
	
	/**
	 * Creates SafeUriBuilder instance from given URI.
	 * @param source is a URI to build from.
	 */
	public SafeUriBuilder(URI source) {
		fromUri(source);
	}
	
	/**
	 * Constructs the SafeUriBuilder from given URI.
	 * @param source is a URI to build from.
	 * @return this instance of SafeUriBuilder.
	 */
	private SafeUriBuilder fromUri(URI source) {
		setScheme(source.getScheme());
		setHost(source.getHost());
		setPort(source.getPort());
		setPath(source.getPath());
		return this;
	}
	
	/**
	 * Builds new path of SafeUriBuilder, which contain's old path with new one appended.
	 * @param path new segment to add.
	 * @return this instance of SafeUriBuilder.
	 */
	public SafeUriBuilder addPath(String path) {
		setPath(getPath() + normalizePath(path));
		return this;
	}
	
	/**
	 * Replaces '/' from the path end and add '/' to beginning, if them don't present.
	 * @param path given new segment of path.
	 * @return normalized path.
	 */
	private String normalizePath(String path) {
		//Avoid NPE
		path = MoreObjects.firstNonNull(path, EMPTY_STRING);
		if (path.endsWith(SLASH)) {
			path = path.substring(0, path.length() - 1);
		}
		if (!path.startsWith(SLASH)) {
			path = SLASH + path; 
		}
		return path;
	}

	@Override
	public URI build() {
		try {		
			return super.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Can't build Uri for: " + getScheme() + getHost() + getPort() + getPath(), e);
		} 
	}

	@Override
	public SafeUriBuilder addParameter(String param, String value) {
		super.addParameter(param, value);
		return this;
	} 
	
	public SafeUriBuilder addParameter(String param, Object value) {
		return this.addParameter(param, String.valueOf(value));
	}

	public SafeUriBuilder addParameter(String param, Date value, Format format) {
		return this.addParameter(param, format.format(value));
	}

	public SafeUriBuilder addParameter(String param, Date value) {
		return this.addParameter(param, value, new SimpleDateFormat(DATE_TIME_FORMAT));
	}
}
