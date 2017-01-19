package ru.bpmink.bpm.model.query;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import ru.bpmink.bpm.model.common.RestEntity;

@SuppressWarnings("unused")
public class SortAttribute extends RestEntity {

    private static final SortOrder DEFAULT_ORDER = SortOrder.ASC;

    //Sorting constants.
    public static final String TAD_DISPLAY_NAME = "TAD_DISPLAY_NAME";
    public static final String PRIORITY = "PRIORITY";
    public static final String CLOSED_BY = "CLOSED_BY";
    public static final String ASSIGNED_TO_ROLE = "ASSIGNED_TO_ROLE";
    public static final String SENT_TIME = "SENT_TIME";
    public static final String PI_NAME = "PI_NAME";
    public static final String READ_TIME = "READ_TIME";
    public static final String PI_MODIFY = "PI_MODIFY";
    public static final String CONTAINMENT_CTX_ID = "CONTAINMENT_CTX_ID";
    public static final String STATUS = "STATUS";
    public static final String COMPLETED = "COMPLETED";
    public static final String PROCESS_APP_ACRONYM = "PROCESS_APP_ACRONYM";
    public static final String PI_STATE = "PI_STATE";
    public static final String PI_PIID = "PI_PIID";
    public static final String DUE = "DUE";
    public static final String PI_STATUS = "PI_STATUS";
    public static final String OWNER = "OWNER";
    public static final String ORIGINATOR = "ORIGINATOR";
    public static final String ACTIVATED = "ACTIVATED";
    public static final String PI_DUE = "PI_DUE";
    public static final String PT_NAME = "PT_NAME";
    public static final String PI_CREATE = "PI_CREATE";
    public static final String PT_DISPLAY_NAME = "PT_DISPLAY_NAME";
    public static final String NAME = "NAME";
    public static final String PI_DISPLAY_NAME = "PI_DISPLAY_NAME";

    public SortAttribute() {

    }

    public SortAttribute(String name, SortOrder sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public SortAttribute(String name) {
        this(name, DEFAULT_ORDER);
    }

    //Sort attribute name.
    @SerializedName("name")
    private String name;

    //Sort order.
    @SerializedName("sortOrder")
    private SortOrder sortOrder;

    /**
     * @return Sort attribute name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Sort order.
     */
    public SortOrder getSortOrder() {
        return MoreObjects.firstNonNull(sortOrder, DEFAULT_ORDER);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

}
