package ru.bpmink.bpm.model.service;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

import java.util.Map;

/**
 * This type represents the result obtained from evaluating a javascript expression
 * within the context of a currently running service
 */
public class ServiceData extends RestEntity {

    private static final Map<String, Object> EMPTY_VARIABLES = Maps.newHashMap();

    //A string which represent the result obtained from evaluating a javascript expression within the
    //context of a currently running service.
    @SerializedName("result")
    private String jsonData;

    //Variables data information stored in an actual Map.
    @SerializedName("resultMap")
    private Map<String, Object> variables = Maps.newHashMap();

    /**
     * @return A string which represent the result obtained from evaluating a javascript expression within the
     *      context of a currently running service.
     */
    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * @return Variables data information stored in an actual Map.
     */
    public Map<String, Object> getVariables() {
        return MoreObjects.firstNonNull(variables, EMPTY_VARIABLES);
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = MoreObjects.firstNonNull(variables, EMPTY_VARIABLES);
    }

}
