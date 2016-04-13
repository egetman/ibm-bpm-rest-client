package ru.bpmink.bpm.api.impl.simple;

import org.apache.http.HttpVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
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
import ru.bpmink.bpm.api.client.*;
import ru.bpmink.util.SafeUriBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Fake (Secure-all) implementation of {@link ru.bpmink.bpm.api.client.BpmClient} which supports {@link org.apache.http.impl.auth.BasicScheme} authentication.
 * Need to be carefully rewrite.
 */
@SuppressWarnings("deprecation")
@Immutable
public class SecuredBpmClient implements BpmClient {
	
	private static final String ROOT_ENDPOINT = "rest/bpm/wle/v1";
	private static final String EXPOSED_ENDPOINT = "exposed";
	private static final String PROCESS_ENDPOINT = "process";
	private static final String TASK_ENDPOINT = "task";
	private static final String TASKS_QUERY_ENDPOINT = "tasks";
	private static final String TASKS_TEMPLATE_QUERY_ENDPOINT = "taskTemplates";
	private static final String PROCESS_QUERY_ENDPOINT = "processes";
	private static final String PROCESS_APPS_ENDPOINT = "processApps";
	
	private ExposedClient exposedClient;
	private ProcessClient processClient;
	private TaskClient taskClient;
	private ProcessAppsClient processAppsClient;
	
	private QueryClient taskQueryClient;
	private QueryClient taskTemplateQueryClient;
	private QueryClient processQueryClient;
	
	private static Logger logger = LoggerFactory.getLogger(SimpleBpmClient.class.getName());
	private final CloseableHttpClient httpClient;
	private final URI rootUri;

	@SuppressWarnings("unused")
	private HttpClientContext httpContext;

	
	public SecuredBpmClient(URI serverUri, String user, String password) {
		logger.info("Start creating bpm client."); 
		this.rootUri = new SafeUriBuilder(serverUri).addPath(ROOT_ENDPOINT).build();
		this.httpClient = createClient(user, password); 
		logger.info("Bpm client created.");
	}
	
	protected CloseableHttpClient createClient(String user, String password) {
		 try {
			 KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	         trustStore.load(null, null);

	         NoSSLSocketFactory sf = new NoSSLSocketFactory(trustStore);
	         sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	         HttpParams params = new BasicHttpParams();
	         HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	         HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	         SchemeRegistry registry = new SchemeRegistry();
	         registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	         registry.register(new Scheme("https", sf, 443));

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

	@Override
	public ExposedClient getExposedClient() {
		if (exposedClient == null) {
			exposedClient = new ExposedClientImpl(new SafeUriBuilder(rootUri).addPath(EXPOSED_ENDPOINT).build(), httpClient, httpContext);
		}
		return exposedClient;
	}

	@Override
	public ProcessClient getProcessClient() {
		if (processClient == null) {
			processClient = new ProcessClientImpl(new SafeUriBuilder(rootUri).addPath(PROCESS_ENDPOINT).build(), httpClient, httpContext);
		}
		return processClient;
	}
	
	@Override
	public TaskClient getTaskClient() {
		if (taskClient == null) {
			taskClient = new TaskClientImpl(new SafeUriBuilder(rootUri).addPath(TASK_ENDPOINT).build(), httpClient, httpContext);
		}
		return taskClient;
	}
	
	@Override
	public ProcessAppsClient getProcessAppsClient() {
		if (processAppsClient == null) {
			processAppsClient = new ProcessAppsClientImpl(new SafeUriBuilder(rootUri).addPath(PROCESS_APPS_ENDPOINT).build(), httpClient, httpContext);
		}
		return processAppsClient;
	}
	
	@Override
	public QueryClient getTaskQueryClient() {
		if (taskQueryClient == null) {
			taskQueryClient = new QueryClientImpl(new SafeUriBuilder(rootUri).addPath(TASKS_QUERY_ENDPOINT).build(), httpClient, httpContext);
		}
		return taskQueryClient;
	}
	
	@Override
	public QueryClient getTaskTemplateQueryClient() {
		if (taskTemplateQueryClient == null) {
			taskTemplateQueryClient = new QueryClientImpl(new SafeUriBuilder(rootUri).addPath(TASKS_TEMPLATE_QUERY_ENDPOINT).build(), httpClient, httpContext);
		}
		return taskTemplateQueryClient;
	}

	@Override
	public QueryClient getProcessQueryClient() {
		if (processQueryClient == null) {
			processQueryClient = new QueryClientImpl(new SafeUriBuilder(rootUri).addPath(PROCESS_QUERY_ENDPOINT).build(), httpClient, httpContext);
		}
		return processQueryClient;
	}

	@Override
	public void close() throws IOException {
		httpClient.close(); 
	}

	public class NoSSLSocketFactory extends SSLSocketFactory {
	    
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public NoSSLSocketFactory(KeyStore trustStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
	        super(trustStore);

	        TrustManager tm = new X509TrustManager() {
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };
	        sslContext.init(null, new TrustManager[] { tm }, null);
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
