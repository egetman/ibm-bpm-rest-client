package ru.bpmink.bpm.model.task;

import com.google.gson.annotations.SerializedName;
import ru.bpmink.bpm.model.common.RestEntity;

public class TaskClientSettings extends RestEntity {

    public TaskClientSettings() {}

    //The URL pointing to the coach.
    @SerializedName("url")
    String url;

    /**
     * @return URL pointing to the coach.
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
