package ru.bpmink.bpm.model.common;

import com.google.gson.annotations.SerializedName;

import static ru.bpmink.util.Constants.COLON;
import static ru.bpmink.util.Constants.FAILS;
import static ru.bpmink.util.Constants.PASSES;
import static ru.bpmink.util.Constants.SEMICOLON;
import static ru.bpmink.util.Constants.SPACE;

/**
 * This class represent an api call result.
 * @param <T> is one of {@link ru.bpmink.bpm.model.common.RestEntity} instances.
 */
public class RestRootEntity<T extends Describable> extends RestEntity {

    //The status of the API call.
    @SerializedName("status")
    private String status;

    //Success API call data.
    @SerializedName("data")
    private T payload;

    //Unsuccessful API call information.
    @SerializedName("Data")
    private RestException exception;

    /**
     * @return true if the API call was unsuccessful, and
     *      {@link ru.bpmink.bpm.model.common.RestRootEntity} contains exception information.
     */
    public boolean isExceptional() {
        return exception != null;
    }

    /**
     * @return Success API call data.
     * @throws ru.bpmink.bpm.model.common.RestException if the API call was unsuccessful,
     *      and {@link ru.bpmink.bpm.model.common.RestRootEntity} contain any exception details.
     */
    public T getPayload() {
        if (isExceptional()) {
            throw exception;
        }
        return payload;
    }

    /**
     * @return The status of the API call.
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    @SuppressWarnings("StringBufferReplaceableByString")
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(COLON).append(SPACE);
        builder.append("Status").append(COLON).append(SPACE);
        builder.append(status).append(SEMICOLON).append(SPACE);
        builder.append("IsExceptional").append(COLON).append(SPACE);
        builder.append((isExceptional() ? PASSES : FAILS));
        return builder.toString();
    }
}
