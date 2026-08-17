package io.github.hyperliquid.sdk.model.order;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response model for {@link io.github.hyperliquid.sdk.apis.Exchange#twapCancel(String, long)}.
 * <p>
 * {@code response} is kept as a raw {@link JsonNode}, same reasoning as
 * {@link TwapOrder}: on a request-level failure it's a plain error string,
 * not the nested {@code {"type":...,"data":{"status":...}}} object success
 * returns.
 * </p>
 */
public class TwapCancelResult {

    private String status;
    private JsonNode response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonNode getResponse() {
        return response;
    }

    public void setResponse(JsonNode response) {
        this.response = response;
    }

    public boolean isSuccess() {
        if (response == null || !response.isObject()) return false;
        JsonNode dataStatus = response.path("data").path("status");
        return dataStatus.isTextual() && "success".equals(dataStatus.asText());
    }

    /**
     * @return the error message, whether it's a request-level failure (plain
     *         string {@code response}) or a cancel-level one (nested
     *         {@code data.status.error}); {@code null} if the cancel succeeded
     */
    public String getError() {
        if (response == null) return null;
        if (response.isTextual()) return response.asText();
        if (response.isObject()) {
            JsonNode dataStatus = response.path("data").path("status");
            if (dataStatus.isObject()) {
                JsonNode error = dataStatus.path("error");
                if (!error.isMissingNode() && !error.isNull()) return error.asText();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "TwapCancelResult{status='" + status + "', response=" + response + '}';
    }
}
