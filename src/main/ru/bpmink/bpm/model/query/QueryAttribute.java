package ru.bpmink.bpm.model.query;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class QueryAttribute extends RestEntity {
	
	public QueryAttribute() {}

	//Attribute name.
	@SerializedName("name")
	private String name;
	
	//Attribute display name (localized).
	@SerializedName("displayName")
	private String displayName;
	
	//Attribute description (localized).
	@SerializedName("description")
	private String description;
	
	//Attribute type.
	@SerializedName("type")
	private AttributeType type;
	
	//Specifies whether the attribute has an array of values.
	@SerializedName("isArray")
	private Boolean isArray;
	
	//Specifies whether the attribute can be used as a filter attribute (default: true).
	@SerializedName("isFilterable")
	private Boolean isFilterable;
	
	//Specifies whether the attribute can be used as a sort attribute (default: true).
	@SerializedName("isSortable")
	private Boolean isSortable;
	
	//The related query and attribute which provides this attributes' information.
	@SerializedName("sourceAttribute")
	private String sourceAttribute;
		
	//A string that identifies attributes with a well-known value range.
	@SerializedName("content")
	private String content;
	
	//The ID of the source attribute table, as defined in the query table definition.
	@SerializedName("sourceQueryTableIdentifier")
	private String sourceQueryTableIdentifier;
	
	/**
	 * @return Attribute name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Attribute display name (localized).
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return Attribute description (localized).
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Attribute type.
	 */
	public AttributeType getType() {
		return type;
	}

	/**
	 * @return Whether the attribute has an array of values.
	 */
	public Boolean isArray() {
		return isArray;
	}

	/**
	 * @return Whether the attribute can be used as a filter attribute (default: true).
	 */
	public Boolean isFilterable() {
		return isFilterable != null && isFilterable;
	}

	/**
	 * @return Whether the attribute can be used as a sort attribute (default: true).
	 */
	public Boolean getIsSortable() {
		return isSortable != null && isSortable;
	}
	
	/**
	 * @return The related query and attribute which provides this attributes' information.
	 */
	public String getSourceAttribute() {
		return sourceAttribute;
	}

	/**
	 * @return A string that identifies attributes with a well-known value range.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return The ID of the source attribute table, as defined in the query table definition.
	 */
	public String getSourceQueryTableIdentifier() {
		return sourceQueryTableIdentifier;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}

	public void setIsFilterable(Boolean isFilterable) {
		this.isFilterable = isFilterable;
	}

	public void setIsSortable(Boolean isSortable) {
		this.isSortable = isSortable;
	}

	public void setSourceAttribute(String sourceAttribute) {
		this.sourceAttribute = sourceAttribute;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSourceQueryTableIdentifier(String sourceQueryTableIdentifier) {
		this.sourceQueryTableIdentifier = sourceQueryTableIdentifier;
	}
}









