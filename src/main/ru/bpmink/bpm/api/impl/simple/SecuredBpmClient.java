package ru.bpmink.bpm.api.impl.simple;

import com.google.common.io.Closeables;

import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import ru.bpmink.bpm.api.client.BpmClient;
import ru.bpmink.bpm.api.client.ExposedClient;
import ru.bpmink.bpm.api.client.ProcessAppsClient;
import ru.bpmink.bpm.api.client.ProcessClient;
import ru.bpmink.bpm.api.client.QueryClient;
import ru.bpmink.bpm.api.client.ServiceClient;
import ru.bpmink.bpm.api.client.TaskClient;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.concurrent.Immutable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Default (Secure-all) implementation of {@link ru.bpmink.bpm.api.client.BpmClient} which
 * supports {@link org.apache.http.impl.auth.BasicScheme} authentication.
 * Need to be carefully rewrite.
 */
@Immutable
@SuppressFBWarnings("JCIP_FIELD_ISNT_FINAL_IN_IMMUTABLE_CLASS")
public final class SecuredBpmClient implements BpmClient {

    private static final String ROOT_ENDPOINT = "rest/bpm/wle/v1";
    private static final String EXPOSED_ENDPOINT = "exposed";
    private static final String PROCESS_ENDPOINT = "process";
    private static final String TASK_ENDPOINT = "task";
    private static final String SERVICE_ENDPOINT = "service";
    private static final String TASKS_QUERY_ENDPOINT = "tasks";
    private static final String TASKS_TEMPLATE_QUERY_ENDPOINT = "taskTemplates";
    private static final String PROCESS_QUERY_ENDPOINT = "processes";
    private static final String PROCESS_APPS_ENDPOINT = "processApps";

    private ExposedClient exposedClient;
    private ProcessClient processClient;
    private TaskClient taskClient;
    private ServiceClient serviceClient;
    private ProcessAppsClient processAppsClient;

    private QueryClient taskQueryClient;
    private QueryClient taskTemplateQueryClient;
    private QueryClient processQueryClient;

    private static Logger logger = LoggerFactory.getLogger(SimpleBpmClient.class.getName());
    private final CloseableHttpClient httpClient;
    private final URI rootUri;

    /**
     * Creates instance of {@link ru.bpmink.bpm.api.impl.simple.KerberosBpmClient}.
     *
     * @param serverUri is a absolute server host/port path.
     * @param user      is a login by which the actions will be performed.
     * @param password  is a user password.
     */
    public SecuredBpmClient(URI serverUri, String user, String password) {
        logger.info("Start creating bpm client.");
        this.rootUri = new SafeUriBuilder(serverUri).addPath(ROOT_ENDPOINT).build();
        this.httpClient = createClient(user, password);
        logger.info("Bpm client created.");
    }

    @SuppressWarnings("deprecation")
    private CloseableHttpClient createClient(String user, String password) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            NoSslSocketFactory socketFactory = new NoSslSocketFactory(trustStore);
            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", socketFactory, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            DefaultHttpClient client = new DefaultHttpClient(ccm, params);
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));

            client.setCredentialsProvider(credentialsProvider);

            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultHttpClient();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExposedClient getExposedClient() {
        if (exposedClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(EXPOSED_ENDPOINT).build();
            exposedClient = new ExposedClientImpl(uri, httpClient, null);
        }
        return exposedClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessClient getProcessClient() {
        if (processClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(PROCESS_ENDPOINT).build();
            processClient = new ProcessClientImpl(uri, httpClient, null);
        }
        return processClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskClient getTaskClient() {
        if (taskClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(TASK_ENDPOINT).build();
            taskClient = new TaskClientImpl(uri, httpClient, null);
        }
        return taskClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceClient getServiceClient() {
        if (serviceClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(SERVICE_ENDPOINT).build();
            serviceClient = new ServiceClientImpl(uri, httpClient, null);
        }
        return serviceClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessAppsClient getProcessAppsClient() {
        if (processAppsClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(PROCESS_APPS_ENDPOINT).build();
            processAppsClient = new ProcessAppsClientImpl(uri, httpClient, null);
        }
        return processAppsClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryClient getTaskQueryClient() {
        if (taskQueryClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(TASKS_QUERY_ENDPOINT).build();
            taskQueryClient = new QueryClientImpl(uri, httpClient, null);
        }
        return taskQueryClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryClient getTaskTemplateQueryClient() {
        if (taskTemplateQueryClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(TASKS_TEMPLATE_QUERY_ENDPOINT).build();
            taskTemplateQueryClient = new QueryClientImpl(uri, httpClient, null);
        }
        return taskTemplateQueryClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryClient getProcessQueryClient() {
        if (processQueryClient == null) {
            final URI uri = new SafeUriBuilder(rootUri).addPath(PROCESS_QUERY_ENDPOINT).build();
            processQueryClient = new QueryClientImpl(uri, httpClient, null);
        }
        return processQueryClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        Closeables.close(httpClient, true);
    }

    @SuppressWarnings("deprecation")
    public class NoSslSocketFactory extends SSLSocketFactory {

        private final SSLContext sslContext = SSLContext.getInstance("TLS");

        /**
         * Layered socket factory for TLS/SSL connections with disabled certificate check.
         *
         * @param trustStore Storage facility for cryptographic keys and certificates {@link java.security.KeyStore}.
         * @throws Exception if there was an exception during initialization.
         */
        NoSslSocketFactory(KeyStore trustStore) throws Exception {

            super(trustStore);
            TrustManager trustManager = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
}
