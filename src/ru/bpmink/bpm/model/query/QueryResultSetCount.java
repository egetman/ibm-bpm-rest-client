package ru.bpmink.bpm.model.query;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class QueryResultSetCount extends RestEntity {

    public QueryResultSetCount() {

    }

    //Number of entities in a query matching specified criteria.
    @SerializedName("count")
    private Integer count;

    /**
     * @return Number of entities in a query matching specified criteria.
     */
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
