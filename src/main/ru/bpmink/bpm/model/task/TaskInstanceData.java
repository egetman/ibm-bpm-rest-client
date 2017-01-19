package ru.bpmink.bpm.model.task;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import ru.bpmink.bpm.model.common.RestEntity;

import java.util.List;
import java.util.Map;

public class TaskInstanceData extends RestEntity {

    private static final Map<String, Object> EMPTY_VARIABLES = Maps.newHashMap();
    private static final List<Object> EMPTY_BUSINESS_DATA = Lists.newArrayList();

    @SuppressWarnings("WeakerAccess")
    public TaskInstanceData() {
    }

    //Variables associated with specified task instance.
    @SerializedName("variables")
    private Map<String, Object> variables = Maps.newHashMap();

    //Data of the process instance containing the task, currently business data.
    @SerializedName("businessData")
    private List<Object> businessData = Lists.newArrayList();

    /**
     * @return Variables associated with specified task instance.
     */
    public Map<String, Object> getVariables() {
        return MoreObjects.firstNonNull(variables, EMPTY_VARIABLES);
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = MoreObjects.firstNonNull(variables, EMPTY_VARIABLES);
    }

    /**
     * @return Data of the process instance containing the task, currently business data.
     */
    public List<Object> getBusinessData() {
        return MoreObjects.firstNonNull(businessData, EMPTY_BUSINESS_DATA);
    }

    public void setBusinessData(List<Object> businessData) {
        this.businessData = MoreObjects.firstNonNull(businessData, EMPTY_BUSINESS_DATA);
    }
}
