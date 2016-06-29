package ru.bpmink.bpm.api.impl.simple;

import com.google.gson.reflect.TypeToken;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import ru.bpmink.bpm.api.client.ExposedClient;
import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.other.exposed.ExposedItems;
import ru.bpmink.bpm.model.other.exposed.Item;
import ru.bpmink.bpm.model.other.exposed.ItemType;
import ru.bpmink.util.SafeUriBuilder;

import javax.annotation.Nonnull;
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

    /**
     * {@inheritDoc}
     */
    @Override
	public RestRootEntity<ExposedItems> listItems() {
		return listItems(rootUri);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public RestRootEntity<ExposedItems> listItems(ItemType itemType) {
		if (itemType == null) {
			return listItems(rootUri);
		}
		return listItems(new SafeUriBuilder(rootUri).addPath(itemType.name().toLowerCase()).build());
	}
	
	private RestRootEntity<ExposedItems> listItems(URI uri) {
		return makeGet(httpClient, httpContext, uri, new TypeToken<RestRootEntity<ExposedItems>>() {});
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws ru.bpmink.bpm.model.common.RestException {@inheritDoc}
     */
    @Override
	public Item getItemByName(@Nonnull String itemName) {
		itemName = Args.notNull(itemName, "Item name");
		return getItem(null, itemName);
	}

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws ru.bpmink.bpm.model.common.RestException {@inheritDoc}
     */
    @Override
	public Item getItemByName(@Nonnull ItemType itemType, @Nonnull String itemName) {
		itemType = Args.notNull(itemType, "Item type");
		itemName = Args.notNull(itemName, "Item name");
		return getItem(itemType, itemName);
	}
	
	private Item getItem(ItemType itemType, String itemName) {
		List<Item> items = listItems(itemType).getPayload().getExposedItems();
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

}
