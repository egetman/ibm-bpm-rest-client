package ru.bpmink.bpm.model.process.definition;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;

public class Timer extends RestEntity {

    //Name of Timer.
    @SerializedName("name")
    private String name;

    //Tokens, assosiated with Timer.
    @SerializedName("tokenID")
    private List<String> tokenIDs = Lists.newArrayList();

    //ID of the step.
    @SerializedName("ID")
    private String id;

    /**
     * @return Name of Timer.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Tokens, assosiated with Timer.
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
