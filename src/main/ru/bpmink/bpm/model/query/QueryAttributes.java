package ru.bpmink.bpm.model.query;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class QueryAttributes extends RestEntity {

	private static final List<QueryAttribute> EMPTY_ATTRIBUTES = Lists.newArrayList();
	private static final List<String> EMPTY_LOCALES = Lists.newArrayList();
	private static final List<SortAttribute> EMPTY_SORTS = Lists.newArrayList();

	public QueryAttributes() {}

	//Identifier of QueryAttributes
	@SerializedName("identifier")
	private String identifier;

	//Query name.
	@SerializedName("query")
	private String query;

	//Query display name (localized).
	@SerializedName("displayName")
	private String displayName;

	//Query description (localized).
	@SerializedName("description")
	private String description;

	//Query kind.
	@SerializedName("kind")
	private QueryKind kind;

	//Type of authorization required for performing queries using this query.
	@SerializedName("authorizationType")
	private AuthorizationType authorizationType;

	//Type of entities that are returned as the result of the query.
	@SerializedName("entityTypeName")
	private EntityType entityType;

	//Name of the attribute uniquely identifying an entity.
	@SerializedName("keyAttribute")
	private String keyAttribute;

	//List of locales defined for the display names and descriptions of this query.
	@SerializedName("locales")
	private List<String> locales = Lists.newArrayList();

	//Ordered list of default sort attribute names.
	@SerializedName("sortAttributes")
	private List<SortAttribute> sortAttributes;

	//Default number of entities that are returned as the result of the query.
	@SerializedName("size")
	private Integer size;

	//List of available query attributes.
	@SerializedName("items")
	private List<QueryAttribute> items = Lists.newArrayList();

	/**
	 * @return Identifier of QueryAttributes
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return Query name.
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return Query display name (localized).
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return Query description (localized).
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Query kind.
	 */
	public QueryKind getKind() {
		return kind;
	}

	/**
	 * @return Type of authorization required for performing queries using this query.
	 */
	public AuthorizationType getAuthorizationType() {
		return authorizationType;
	}

	/**
	 * @return Type of entities that are returned as the result of the query.
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * @return Name of the attribute uniquely identifying an entity.
	 */
	public String getKeyAttribute() {
		return keyAttribute;
	}

	/**
	 * @return List of locales defined for the display names and descriptions of
	 *         this query.
	 */
	public List<String> getLocales() {
		return MoreObjects.firstNonNull(locales, EMPTY_LOCALES);
	}

	/**
	 * @return Ordered list of default sort attribute names.
	 */
	public List<SortAttribute> getSortAttributes() {
		return MoreObjects.firstNonNull(sortAttributes, EMPTY_SORTS);
	}
	
	/**
	 * @return List of available query attributes.
	 */
	public List<QueryAttribute> getItems() {
		return MoreObjects.firstNonNull(items, EMPTY_ATTRIBUTES);
	}


	/**
	 * @return Default number of entities that are returned as the result of the query.
	 */
	public Integer getSize() {
		return size;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setKind(QueryKind kind) {
		this.kind = kind;
	}

	public void setAuthorizationType(AuthorizationType authorizationType) {
		this.authorizationType = authorizationType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public void setKeyAttribute(String keyAttribute) {
		this.keyAttribute = keyAttribute;
	}

	public void setLocales(List<String> locales) {
		this.locales = locales;
	}

	public void setSortAttributes(List<SortAttribute> sortAttributes) {
		this.sortAttributes = sortAttributes;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setItems(List<QueryAttribute> items) {
		this.items = items;
	}

}










	
	
	

