package ru.bpmink.bpm.api.client;


import ru.bpmink.bpm.model.common.RestRootEntity;
import ru.bpmink.bpm.model.other.exposed.ExposedItems;
import ru.bpmink.bpm.model.other.exposed.Item;
import ru.bpmink.bpm.model.other.exposed.ItemType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: Add full api possibilities
/**
 * Client for exposed api actions.
 */
public interface ExposedClient {

	/**
	 * Retrieve items of all types that are exposed to an end user.
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity<ru.bpmink.bpm.model.other.exposed.ExposedItems>}
	 */
	RestRootEntity<ExposedItems> listItems();
	
	/**
	 * Retrieve items of a specific type that are exposed to an end user.
	 * If itemType is null, then all service subtypes will be included in the result set.
	 * @param itemType is a filter of items (see {@link ru.bpmink.bpm.model.other.exposed.ItemType})
	 * @return {@link ru.bpmink.bpm.model.common.RestRootEntity<ru.bpmink.bpm.model.other.exposed.ExposedItems>}
	 */
	RestRootEntity<ExposedItems> listItems(@Nullable ItemType itemType);
	
	/**
	 * Retrieve item by the specified name.
	 * If itemName is null, then {@link IllegalArgumentException} should be thrown.
	 * Note that it's a synchronous call.
	 * @param itemName is a full name of item {@link ru.bpmink.bpm.model.other.exposed.Item#getName()}
	 * @return {@link ru.bpmink.bpm.model.other.exposed.Item}
	 * @throws IllegalArgumentException if itemName is null.
	 * @throws ru.bpmink.bpm.model.common.RestException if the api call was unsuccessful.
	 */
	Item getItemByName(@Nonnull String itemName);

	/**
	 * Retrieve item by the specified name and type.
	 * If itemName or itemType is null, then {@link IllegalArgumentException} should be thrown.
	 * Note that it's a synchronous call.
	 * @param itemName is a full name of item {@link ru.bpmink.bpm.model.other.exposed.Item#getName()}
	 * @param itemType is a filter of items {@link ru.bpmink.bpm.model.other.exposed.ItemType}
	 * @return {@link ru.bpmink.bpm.model.other.exposed.Item}
	 * @throws IllegalArgumentException if itemName or itemType are null
	 * @throws ru.bpmink.bpm.model.common.RestException if the api call was unsuccessful.
	 */
	Item getItemByName(@Nonnull ItemType itemType, @Nonnull String itemName);
	
}
