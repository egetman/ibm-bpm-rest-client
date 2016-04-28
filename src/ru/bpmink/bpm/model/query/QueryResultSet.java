package ru.bpmink.bpm.model.query;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class QueryResultSet extends RestEntity {

	private static final List<QueryAttribute> EMPTY_QUERY_ATTRIBUTE = Lists.newArrayList();
	private static final List<QueryResult> EMPTY_QUERY_RESULT = Lists.newArrayList();
	
	public QueryResultSet() {}

	//Name of query result set attribute identifying an entity.
	@SerializedName("identifier")
	private String identifier;
	
	//Name of the query that is associated with this query result set.
	@SerializedName("query")
	private String query;
	
	//Type of entities that are returned as the result of the query.
	@SerializedName("entityTypeName")
	private EntityType entityType;
	
	//List of query attributes.
	@SerializedName("attributeInfo")
	private List<QueryAttribute> attributes = Lists.newArrayList();
	
	//The offset value that was specified on the original request. This value represents the index
	//(within the overall query result set) of the first item being returned.
	@SerializedName("offset")
	private Integer offset;
	
	//The number of query results being returned.
	@SerializedName("size")
	private Integer size;
	
	//The number of query results requested in the original request.
	@SerializedName("requestedSize")
	private Integer requestedSize;
	
	//Total number of results.
	@SerializedName("totalCount")
	private Integer totalCount;
	
	//True if total number of results exceeds a search limit and is actually unknown.
	@SerializedName("countLimitExceeded")
	private Boolean countLimitExceeded;
	
	//Specifies the search limit used if any or 0 if it's not set.
	@SerializedName("countLimit")
	private Integer countLimit;
	
	//Entities contained in this query result set; list of attribute names and values, as selected by
	// selectedAttributes, plus the entities' key (using its source attribute name).
	@SerializedName("items")
	private List<QueryResult> queryResults = Lists.newArrayList();

	
	/**
	 * @return Identifier of QueryList.
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * @return Name of the query that is associated with this query result set.
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return Type of entities that are returned as the result of the query.
	 */
	public EntityType getEntityType() {
		return entityType;
	}
	
	/**
	 * @return List of query attributes.
	 */
	public List<QueryAttribute> getAttributes() {
		return MoreObjects.firstNonNull(attributes, EMPTY_QUERY_ATTRIBUTE);
	}

	/**
	 * @return The offset value that was specified on the original request.
	 * 			This value represents the index (within the overall query result set) of the first item being returned.
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * @return The number of query results being returned.
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @return The number of query results requested in the original request.
	 */
	public Integer getRequestedSize() {
		return requestedSize;
	}

	/**
	 * @return Total number of results.
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	
	/**
	 * @return True if total number of results exceeds a search limit and is actually unknown.
	 */
	public Boolean isCountLimitExceeded() {
		return countLimitExceeded;
	}

	/**
	 * @return Specifies the search limit used if any or 0 if it's not set.
	 */
	public Integer getCountLimit() {
		return countLimit;
	}
	
	/**
	 * @return Entities contained in this query result set; list of attribute names and values, as selected
	 * 			by selectedAttributes, plus the entities' key (using its source attribute name).
	 * 			(see {@link ru.bpmink.bpm.model.query.QueryResult});
	 */
	public List<QueryResult> getQueryResults() {
		return MoreObjects.firstNonNull(queryResults, EMPTY_QUERY_RESULT);
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public void setAttributes(List<QueryAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setRequestedSize(Integer requestedSize) {
		this.requestedSize = requestedSize;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setCountLimitExceeded(Boolean countLimitExceeded) {
		this.countLimitExceeded = countLimitExceeded;
	}

	public void setCountLimit(Integer countLimit) {
		this.countLimit = countLimit;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setQueryResults(List<QueryResult> queryResults) {
		this.queryResults = queryResults;
	}

}
