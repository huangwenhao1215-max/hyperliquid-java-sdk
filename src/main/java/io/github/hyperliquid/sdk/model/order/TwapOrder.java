package io.github.hyperliquid.sdk.model.order;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response model for {@link io.github.hyperliquid.sdk.apis.Exchange#twapOrder(TwapOrderRequest)}.
 * <p>
 * {@code response} is kept as a raw {@link JsonNode} rather than a typed
 * class: on a request-level failure (bad wallet, malformed action, ...) HL
 * returns {@code {"status":"err","response":"<plain error string>"}} --
 * {@code response} is a bare string, not the nested
 * {@code {"type":...,"data":{"status":{...}}}} object it is on success.
 * Forcing a fixed shape onto this field crashes Jackson the moment a
 * request-level error is hit (as opposed to an order-level error, which
 * still nests under the object shape's {@code data.status.error}).
 * </p>
 */
public class TwapOrder {

    /**
     * Top-level status string from the exchange (e.g. {@code "ok"} or {@code "err"}).
     */
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

    /**
     * @return the assigned TWAP id, or {@code null} if the request failed
     */
    public Long getTwapId() {
        if (response == null || !response.isObject()) return null;
        JsonNode twapId = response.path("data").path("status").path("running").path("twapId");
        return twapId.isMissingNode() || twapId.isNull() ? null : twapId.asLong();
    }

    /**
     * @return the error message, whether it's a request-level failure (plain
     *         string {@code response}) or an order-level one (nested
     *         {@code data.status.error}); {@code null} if the request succeeded
     */
    public String getError() {
        if (response == null) return null;
        if (response.isTextual()) return response.asText();
        if (response.isObject()) {
            JsonNode error = response.path("data").path("status").path("error");
            if (!error.isMissingNode() && !error.isNull()) return error.asText();
        }
        return null;
    }

    @Override
    public String toString() {
        return "TwapOrder{status='" + status + "', response=" + response + '}';
    }
}
