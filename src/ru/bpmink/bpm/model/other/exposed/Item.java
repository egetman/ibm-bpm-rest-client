package ru.bpmink.bpm.model.other.exposed;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.Date;

public class Item extends RestEntity {
	
	public Item() {}
	
	//The exposed ID of the item.
	@SerializedName("ID")
	private String id;
	
	//The type associated with the exposed item: 'process', 'service', 'scoreboard', or 'report'.
	@SerializedName("type")
	private ItemType itemType;
	
	//The subtype associated with the exposed item of type 'service': 'not_exposed', 'administration_service', 'startable_service', 'dashboard', or 'url'.
	@SerializedName("subtype")
	private SubType sybType;
	
	//The exposed URL of the item; use this to view or run the item.
	@SerializedName("runURL")
	private String runUrl;
	
	//The item's ID; this will be based on the type of the item.
	@SerializedName("itemID")
	private String itemId;
	
	//The item's reference; this will be based on the type of the item.
	@SerializedName("itemReference")
	private String itemReference;
	
	//The ID of the process application associated with this item.
	@SerializedName("processAppID")
	private String processAppId;
	
	//The title of the process application associated with this item.
	@SerializedName("processAppName")
	private String processAppName;
	
	//The acronym of the process application associated with this item.
	@SerializedName("processAppAcronym")
	private String processAppAcronym;

	//The ID of the snapshot associated with this item.
	@SerializedName("snapshotID")
	private String snapshotId;
	
	//The title of the snapshot associated with this item.
	@SerializedName("snapshotName")
	private String snapshotName;
	
	//The time, when snapshot is created
	@SerializedName("snapshotCreatedOn")
	private Date snapshotCreatedOn;
	
	//The display name of the item; this will be the name of the Process, Service, Scoreboard or Report.
	@SerializedName("display")
	private String name;

	//The ID of the branch (track) associated with this item.
	@SerializedName("branchID")
	private String branchId;
	
	//The branch (track) name associated with this item.
	@SerializedName("branchName")
	private String branchName;
	
	//If startable via a REST api, a relative URL that can start the item.
	@SerializedName("startURL")
	private String startUrl;
	
	@SerializedName("isDefault")
	private Boolean isDefault;
	
	@SerializedName("tip")
	private Boolean tip;

	/**
	 * @return The exposed ID of the item.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return The type associated with the exposed item: 'process', 'service', 'scoreboard', or 'report'.
	 */
	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return The subtype associated with the exposed item of type 'service': 'not_exposed', 'administration_service', 'startable_service', 'dashboard', or 'url'.
	 */
	public SubType getSybType() {
		return sybType;
	}

	public void setSybType(SubType sybType) {
		this.sybType = sybType;
	}

	/**
	 * @return The exposed URL of the item; use this to view or run the item.
	 */
	public String getRunUrl() {
		return runUrl;
	}

	public void setRunUrl(String runUrl) {
		this.runUrl = runUrl;
	}

	/**
	 * @return The item's ID; this will be based on the type of the item.
	 */
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return The item's reference; this will be based on the type of the item.
	 */
	public String getItemReference() {
		return itemReference;
	}

	public void setItemReference(String itemReference) {
		this.itemReference = itemReference;
	}

	/**
	 * @return The ID of the process application associated with this item.
	 */
	public String getProcessAppId() {
		return processAppId;
	}

	public void setProcessAppId(String processAppId) {
		this.processAppId = processAppId;
	}
	
	/**
	 * @return The acronym of the process application associated with this item.
	 */
	public String getProcessAppAcronym() {
		return processAppAcronym;
	}

	public void setProcessAppAcronym(String processAppAcronym) {
		this.processAppAcronym = processAppAcronym;
	}

	/**
	 * @return The ID of the snapshot associated with this item.
	 */
	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}
	

	/**
	 * @return The title of the snapshot associated with this item.
	 */
	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	/**
	 * @return The time, when snapshot is created.
	 */
	public Date getSnapshotCreatedOn() {
		return snapshotCreatedOn;
	}

	/**
	 * @param snapshotCreatedOn the snapshotCreatedOn to set
	 */
	public void setSnapshotCreatedOn(Date snapshotCreatedOn) {
		this.snapshotCreatedOn = snapshotCreatedOn;
	}

	/**
	 * @return The display name of the item; this will be the name of the Process, Service, Scoreboard or Report.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The title of the process application associated with this item.
	 */
	public String getProcessAppName() {
		return processAppName;
	}

	public void setProcessAppName(String processAppName) {
		this.processAppName = processAppName;
	}

	/**
	 * @return The ID of the branch (track) associated with this item.
	 */
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return The branch (track) name associated with this item.
	 */
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return If startable via a REST api, a relative URL that can start the item.
	 */
	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	/**
	 * @return the isDefault
	 */
	public Boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the tip
	 */
	public Boolean isTip() {
		return tip;
	}

	public void setTip(Boolean tip) {
		this.tip = tip;
	}

} 
	
