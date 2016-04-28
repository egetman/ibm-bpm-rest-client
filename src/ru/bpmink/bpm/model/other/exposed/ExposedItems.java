package ru.bpmink.bpm.model.other.exposed;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class ExposedItems extends RestEntity {

	private static final List<Item> EMPTY_LIST = Lists.newArrayList();
	
	public ExposedItems() {}
	
	@SerializedName("exposedItemsList")
	private List<Item> exposedItems = Lists.newArrayList();
	
	//To avoid null checks after de-serialization.
	public List<Item> getExposedItems() {
		return MoreObjects.firstNonNull(exposedItems, EMPTY_LIST);
	}
	
}
