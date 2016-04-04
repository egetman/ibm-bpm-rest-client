package ru.bpmink.bpm.api.impl;


import ru.bpmink.bpm.api.client.BpmClient;
import ru.bpmink.bpm.api.impl.simple.KerberosBpmClient;
import ru.bpmink.bpm.api.impl.simple.SecuredBpmClient;
import ru.bpmink.bpm.api.impl.simple.SimpleBpmClient;

import java.net.URI;

public class BpmClientFactory {

	private static final String HTTP_SCHEME = "http";
	private static final String HTTPS_SCHEME = "https";

	private BpmClientFactory() {}
	
	/**
	 * Creates the Bpm client object with given parameters.
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
	 */
	public static BpmClient createClient(URI serverUri, String user, String password) {
		if (serverUri == null) {
			throw new IllegalArgumentException("Server uri must be specified");
		}
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
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
	 */
	public static BpmClient createClient(String serverUri, String user, String password) {
		if (serverUri == null) {
			throw new IllegalArgumentException("Server uri must be specified");
		}
		return createClient(URI.create(serverUri), user, password);
	}

	/**
	 * Creates the Bpm client object with given parameters.
	 * @param serverUri is a absolute server host/port path.
	 * @param user is a login by which the actions will be performed.
	 * @param password is a user password.
	 * @param domain is an identification string that defines a realm of administrative autonomy, authority or control.
	 * @param kdc key distribution center (KDC) is part of a cryptosystem intended to reduce the risks inherent in exchanging keys.
	 * @return {@link ru.bpmink.bpm.api.client.BpmClient} instance.
	 */
	public static BpmClient createClient(URI serverUri, String user, String password, String domain, String kdc) {
		return new KerberosBpmClient(serverUri, user, password, domain, kdc);
	}
	
}
