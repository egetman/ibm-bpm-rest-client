package ru.groomsh.bpm.api.impl.simple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import ru.groomsh.bpm.api.client.ExposedClient;
import ru.groomsh.bpm.model.other.exposed.ExposedItems;
import ru.groomsh.bpm.model.other.exposed.Item;
import ru.groomsh.bpm.model.other.exposed.ItemType;
import ru.groomsh.util.SafeUriBuilder;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Immutable
public class ExposedClientImpl extends BaseClient implements ExposedClient {

	private static final Item EMPTY_ITEM = new Item();
	
	private final URI rootUri;
	private final HttpClient httpClient;
	private final HttpContext httpContext;
	
	ExposedClientImpl(URI rootUri, HttpClient httpClient, HttpContext httpContext) {
		this.httpClient = httpClient;
		this.rootUri = rootUri;
		this.httpContext = httpContext;
	}
	
	ExposedClientImpl(URI rootUri, HttpClient httpClient) {
		this(rootUri, httpClient, null);
	}
	
	@Override
	public ExposedItems listItems() {
		return listItems(rootUri);
	}

	@Override
	public ExposedItems listItems(ItemType itemType) {
		if (itemType == null) {
			return listItems(rootUri);
		}
		return listItems(new SafeUriBuilder(rootUri).addPath(itemType.name().toLowerCase()).build());
	}
	
	private ExposedItems listItems(URI uri) {
		HttpGet request = new HttpGet(uri);
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
			throw new RuntimeException("Can't get ExposedItems object from Server with uri: " + rootUri, e);
		} 

		logResponse(response, body); 
		request.releaseConnection();
		
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_FORMAT).create();
		return gson.fromJson(body, ExposedItems.class);
	}

	@Override
	public Item getItemByName(@Nonnull String itemName) {
		validateParameters(itemName);
		return getItem(null, itemName);
	}

	@Override
	public Item getItemByName(@Nonnull ItemType itemType, @Nonnull String itemName) {
		validateParameters(itemType, itemName);
		return getItem(itemType, itemName);
	}
	
	private Item getItem(ItemType itemType, String itemName) {
		List<Item> items = listItems(itemType).getPayload().getExposedItemsList();
		for (Item item : items) {
			ItemType type = item.getItemType();
			switch (type) {
				case PROCESS:
					if (itemName.equalsIgnoreCase(item.getName())) {
						return item;
					}
				case SERVICE:
					if (itemName.equalsIgnoreCase(item.getName())) {
						return item;
					}
				case REPORT:
					//TODO: don't know by which param make search - ProcessAppName or Display, so it's empty for now.
				case SCOREBOARD:
					
			}
		}
		return EMPTY_ITEM;		
	}
	
	private void validateParameters(Object... objects) {
		for (Object object : objects) {
			if (object == null) {
				throw new IllegalArgumentException("Parameter is mandatory");
			}
		}
	}
	

}
