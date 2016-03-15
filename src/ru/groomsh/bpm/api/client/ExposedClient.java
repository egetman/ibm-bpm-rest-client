package ru.groomsh.bpm.api.client;


import ru.groomsh.bpm.model.other.exposed.ExposedItems;
import ru.groomsh.bpm.model.other.exposed.Item;
import ru.groomsh.bpm.model.other.exposed.ItemType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: Add full api possibilities
/**
 * Client for exposed api actions.
 */
public interface ExposedClient {

	/**
	 * Retrieve items of all types that are exposed to an end user.
	 * @return {@link ru.groomsh.bpm.model.other.exposed.ExposedItems}
	 */
	ExposedItems listItems();
	
	/**
	 * Retrieve items of a specific type that are exposed to an end user.
	 * If itemType is null, then all service subtypes will be included in the result set.
	 * @param itemType is a filter of items (see {@link ru.groomsh.bpm.model.other.exposed.ItemType})
	 * @return {@link ru.groomsh.bpm.model.other.exposed.ExposedItems}
	 */
	ExposedItems listItems(@Nullable ItemType itemType);
	
	/**
	 * Retrieve item by the specified name.
	 * If itemName is null, then {@link IllegalArgumentException} should be thrown.
	 * @param itemName is a full name of item (see {@link ru.groomsh.bpm.model.other.exposed.Item#getName()})
	 * @return {@link ru.groomsh.bpm.model.other.exposed.Item}
	 * @throws IllegalArgumentException if itemName is null
	 */
	Item getItemByName(@Nonnull String itemName);

	/**
	 * Retrieve item by the specified name and type.
	 * If itemName or itemType is null, then {@link IllegalArgumentException} should be thrown.
	 * @param itemName is a full name of item (see {@link ru.groomsh.bpm.model.other.exposed.Item#getName()})
	 * @param itemType is a filter of items (see {@link ru.groomsh.bpm.model.other.exposed.ItemType})
	 * @return {@link ru.groomsh.bpm.model.other.exposed.Item}
	 * @throws IllegalArgumentException if itemName or itemType are null
	 */
	Item getItemByName(@Nonnull ItemType itemType, @Nonnull String itemName);
	
}
