package ru.bpmink.bpm.model.query;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class QueryList extends RestEntity {
	
	private static final List<Query> EMPTY_LIST = Lists.newArrayList();
	
	public QueryList() {}

	//Identifier of QueryList 
	@SerializedName("identifier")
	private String identifier;
	
	//List of available queries
	@SerializedName("items")
	private List<Query> queries = Lists.newArrayList();

	/**
	 * @return Identifier of QueryList.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return List of available queries (see {@link ru.bpmink.bpm.model.query.Query});
	 */
	public List<Query> getQueries() {
		return MoreObjects.firstNonNull(queries, EMPTY_LIST);
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setQueries(List<Query> queries) {
		this.queries = queries;
	}

}
