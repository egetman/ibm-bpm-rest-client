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
     *
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains information about
     *      all exposed to an end user entities: {@link ru.bpmink.bpm.model.other.exposed.ExposedItems}.
     */
    RestRootEntity<ExposedItems> listItems();

    /**
     * Retrieve items of a specific type that are exposed to an end user.
     * If itemType is null, then all service subtypes will be included in the result set.
     *
     * @param itemType is a filter of items (see {@link ru.bpmink.bpm.model.other.exposed.ItemType})
     * @return {@link ru.bpmink.bpm.model.common.RestRootEntity} instance, that contains information about
     *      all exposed to an end user entities of specified {@literal itemType}:
     *      {@link ru.bpmink.bpm.model.other.exposed.ExposedItems}. If parameter {@literal itemType} is null,
     *      return will be similar to call {@link #listItems()}.
     */
    RestRootEntity<ExposedItems> listItems(@Nullable ItemType itemType);

    /**
     * Retrieve item by the specified name.
     * If itemName is null, then {@link IllegalArgumentException} should be thrown.
     * <p>Note: default wle rest api don't allows to extract only one, specified item.
     * So sorting and filtering are performed on client side. That's why, if api call was unsuccessful, there are
     * possibility of throwing {@link ru.bpmink.bpm.model.common.RestException}.</p>
     *
     * @param itemName is a full name of item {@link ru.bpmink.bpm.model.other.exposed.Item#getName()}
     * @return {@link ru.bpmink.bpm.model.other.exposed.Item} instance with given {@literal itemName}.
     *      If item with given {@literal itemName} not found, it implementation-dependent to return {@literal null} or
     *      {@literal NULL_OBJECT}.
     * @throws IllegalArgumentException                 if itemName is null.
     * @throws ru.bpmink.bpm.model.common.RestException if the api call was unsuccessful.
     */
    @SuppressWarnings("SameParameterValue")
    Item getItemByName(@Nonnull String itemName);

    /**
     * Retrieve item by the specified name and type.
     * If itemName or itemType is null, then {@link IllegalArgumentException} should be thrown.
     * <p>Note: default wle rest api don't allows to extract only one, specified item.
     * So sorting and filtering are performed on client side. That's why, if api call was unsuccessful, there are
     * possibility of throwing {@link ru.bpmink.bpm.model.common.RestException}.</p>
     *
     * @param itemName is a full name of item {@link ru.bpmink.bpm.model.other.exposed.Item#getName()}
     * @param itemType is a filter of items {@link ru.bpmink.bpm.model.other.exposed.ItemType}
     * @return {@link ru.bpmink.bpm.model.other.exposed.Item} instance with given {@literal itemName}
     *      and {@literal itemType}.
     *      If item with given {@literal itemName} and {@literal itemType} not found, it implementation-dependent
     *      to return {@literal null} or {@literal NULL_OBJECT}.
     * @throws IllegalArgumentException                 if itemName or itemType are null
     * @throws ru.bpmink.bpm.model.common.RestException if the api call was unsuccessful.
     */
    Item getItemByName(@Nonnull ItemType itemType, @Nonnull String itemName);

}
