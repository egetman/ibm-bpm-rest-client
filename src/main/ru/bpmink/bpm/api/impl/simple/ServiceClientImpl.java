package ru.bpmink.bpm.api.impl.simple;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import ru.bpmink.bpm.api.client.ServiceClient;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.service.ServiceData;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.net.URI;
import java.util.Map;

@Immutable
final class ServiceClientImpl extends BaseClient implements ServiceClient {

    private final URI rootUri;
    private final HttpClient httpClient;
    private final HttpContext httpContext;

    //Request parameters constants
    private static final String FIELD = "field";
    private static final String VALUE = "value";
    private static final String PARAMS = "params";
    private static final String ACTION = "action";

    private static final String ACTION_GET_DATA = "getData";
    private static final String ACTION_SET_DATA = "setData";


    ServiceClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
        this.httpClient = httpClient;
        this.rootUri = rootUri;
        this.httpContext = httpContext;
    }

    ServiceClientImpl(URI rootUri, HttpClient httpClient) {
        this(rootUri, httpClient, null);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public RestRootEntity<ServiceData> getServiceData(@Nonnull String instanceId, String... fields) {
        instanceId = Args.notNull(instanceId, "Instance id (instanceId)");
        SafeUriBuilder uri = new SafeUriBuilder(rootUri).addPath(instanceId).addParameter(ACTION, ACTION_GET_DATA);

        if (fields != null && fields.length > 0) {
            uri.addParameter("fields", Joiner.on(DEFAULT_SEPARATOR).join(fields));
        }

        return makeGet(httpClient, httpContext, uri.build(), new TypeToken<RestRootEntity<ServiceData>>() {});
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public RestRootEntity<ServiceData> setServiceData(@Nonnull String instanceId,
                                                      @Nonnull Map<String, Object> parameters) {
        instanceId = Args.notNull(instanceId, "Instance id (instanceId)");
        parameters = Args.notNull(parameters, "Variables (parameters)");
        Args.notEmpty(parameters.keySet(), "Parameters names");
        Args.notEmpty(parameters.values(), "Parameters values");

        Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
        String params = gson.toJson(parameters);


        URI uri = new SafeUriBuilder(rootUri).addPath(instanceId).addParameter(ACTION, ACTION_SET_DATA)
                .addParameter(PARAMS, params).build();

        return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<ServiceData>>() {});
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public RestRootEntity<ServiceData> setServiceData(@Nonnull String instanceId, @Nonnull String field,
                                                      @Nonnull Object value) {
        instanceId = Args.notNull(instanceId, "Instance id (instanceId)");
        field = Args.notNull(field, "Field name (field)");
        value = Args.notNull(value, "Field value (value)");
        Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
        value = gson.toJson(value);

        URI uri = new SafeUriBuilder(rootUri).addPath(instanceId).addParameter(ACTION, ACTION_SET_DATA)
                .addParameter(FIELD, field).addParameter(VALUE, value).build();

        return makePost(httpClient, httpContext, uri, new TypeToken<RestRootEntity<ServiceData>>() {});
    }


}
