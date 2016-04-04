package ru.bpmink.bpm.model.process.definition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class EventHandler extends RestEntity {

    //Name of EventHandler.
    @SerializedName("name")
    private String name;

    //Event type: Intermediate, Start, End. TODO:Need to check actual types.
    @SerializedName("eventType")
    private String eventType;

    //Tokens, assosiated with EventHandler.
    @SerializedName("tokenID")
    private List<String> tokenIDs = Lists.newArrayList();

    //ID of the step.
    @SerializedName("ID")
    private String id;

    /**
     * @return Name of EventHandler.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Event type: Intermediate, Start, End.
     */
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return Tokens, assosiated with EventHandler.
     */
    public List<String> getTokenIDs() {
        return tokenIDs;
    }

    public void setTokenIDs(List<String> tokenIDs) {
        this.tokenIDs = tokenIDs;
    }

    /**
     * @return ID of the step.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
