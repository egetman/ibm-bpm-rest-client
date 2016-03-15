package ru.groomsh.bpm.model.other.exposed;

import com.google.gson.annotations.SerializedName;

public enum SubType {
	
	@SerializedName("not_exposed")
	NOT_EXPOSED,
	@SerializedName("administration_service")
	ADMINISTRATION_SERVICE,
	@SerializedName("startable_service")
	STARTABLE_SERVICE,
	@SerializedName("dashboard")
	DASHBOARD,
	@SerializedName("url")
	URL
}
