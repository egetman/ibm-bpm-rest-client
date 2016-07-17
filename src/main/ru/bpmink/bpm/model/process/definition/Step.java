package ru.bpmink.bpm.model.process.definition;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class Step extends RestEntity {

    private static final List<Line> EMPTY_LINES = Lists.newArrayList();

    public Step() {}

    //Name of the current step
    @SerializedName("name")
    private String name;

    //Type of the element
    @SerializedName("type")
    private ElementType type;

    //Type of the activity
    @SerializedName("activityType")
    private String activityType;

    //Id of the external activity
    @SerializedName("externalID")
    private String externalId;

    //Name of the associated swimlane
    @SerializedName("lane")
    private String lane;

    //Coordinates of the element
    @SerializedName("x")
    private Integer pointX;

    @SerializedName("y")
    private Integer pointY;

    //Color of the element in diagram
    @SerializedName("color")
    private Color color;

    //Attached timer for that element
    @SerializedName("attachedTimer")
    private List<Timer> attachedTimers;

    //Attached EventHandler for that element
    @SerializedName("attachedEventHandler")
    private List<EventHandler> attachedEventHandlers = Lists.newArrayList();

    //Sequence flows from the element
    @SerializedName("lines")
    private List<Line> lines = Lists.newArrayList();

    //ID of the step
    @SerializedName("ID")
    private String id;

    /**
     * @return Name of the current step.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Type of the element.
     */
    public ElementType getType() {
        return type;
    }

    /**
     * @return Type of the activity.
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * @return Id of the external activity.
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * @return Name of the associated swimlane.
     */
    public String getLane() {
        return lane;
    }

    /**
     * @return The pointX coordinate.
     */
    public Integer getPointX() {
        return pointX;
    }

    /**
     * @return The pointY coordinate.
     */
    public Integer getPointY() {
        return pointY;
    }

    /**
     * @return Color of the element in diagram.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Attached timers for that element.
     */
    public List<Timer> getAttachedTimers() {
        return attachedTimers;
    }

    /**
     * @return Attached EventHandlers for that element.
     */
    public List<EventHandler> getAttachedEventHandlers() {
        return attachedEventHandlers;
    }

    /**
     * @return Sequence flows from the element.
     */
    public List<Line> getLines() {
        return MoreObjects.firstNonNull(lines, EMPTY_LINES);
    }

    /**
     * @return ID of the step.
     */
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public void setPointX(Integer pointX) {
        this.pointX = pointX;
    }

    public void setPointY(Integer pointY) {
        this.pointY = pointY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAttachedTimer(List<Timer> attachedTimers) {
        this.attachedTimers = attachedTimers;
    }

    public void setAttachedEventHandlers(List<EventHandler> attachedEventHandlers) {
        this.attachedEventHandlers = attachedEventHandlers;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void setId(String id) {
        this.id = id;
    }

}
