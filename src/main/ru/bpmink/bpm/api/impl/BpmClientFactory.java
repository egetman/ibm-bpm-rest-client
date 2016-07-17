package ru.bpmink.bpm.api.impl;


import org.apache.http.util.Args;
import ru.bpmink.bpm.api.client.BpmClient;
import ru.bpmink.bpm.api.impl.simple.KerberosBpmClient;
import ru.bpmink.bpm.api.impl.simple.SecuredBpmClient;
import ru.bpmink.bpm.api.impl.simple.SimpleBpmClient;

import java.net.URI;

/**
 * Factory class for different rest client implementations.
 */
public class BpmClientFactory {

	private static final String HTTP_SCHEME = "http";
	private static final String HTTPS_SCHEME = "https";

	private BpmClientFactory() {}
	
	/**
	 * Creates the Bpm client object with given parameters.
     *
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
     * @throws IllegalArgumentException if {@literal serverUri} is null, or {@code serverUri.getScheme} returns
     *      null or value not in [{@literal http}, {@literal https}].
	 */
	public static BpmClient createClient(URI serverUri, String user, String password) {
        serverUri = Args.notNull(serverUri, "Server uri (serverUri)");
        if (HTTP_SCHEME.equals(serverUri.getScheme())) {
			return new SimpleBpmClient(serverUri, user, password);
		} else if (HTTPS_SCHEME.equals(serverUri.getScheme())) {
			return new SecuredBpmClient(serverUri, user, password);
		} else {
			throw new IllegalArgumentException("Unknown scheme: " + serverUri.getScheme());
		}
	}
	
	/**
	 * Creates the Bpm client object with given parameters.
     *
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
     * @throws IllegalArgumentException if {@literal serverUri} is null.
	 */
	public static BpmClient createClient(String serverUri, String user, String password) {
        serverUri = Args.notNull(serverUri, "Server uri (serverUri)");
		return createClient(URI.create(serverUri), user, password);
	}

	/**
	 * Creates the Bpm client object with given parameters.
     *
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
     * @param domain is an identification string that defines a realm of administrative autonomy, authority or control.
	 * @param kdc key distribution center ({@literal KDC}) is part of a cryptosystem intended to reduce the risks
     *      inherent in exchanging keys.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
     * @throws IllegalArgumentException if {@literal serverUri} is null.
	 */
	public static BpmClient createClient(URI serverUri, String user, String password, String domain, String kdc) {
        serverUri = Args.notNull(serverUri, "Server uri (serverUri)");
		return new KerberosBpmClient(serverUri, user, password, domain, kdc);
	}
	
}
