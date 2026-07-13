package io.github.hyperliquid.sdk.model.userabstraction;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response model for {@link io.github.hyperliquid.sdk.apis.Exchange#agentSetAbstraction(String)}.
 */
public class AgentSetAbstraction {

    /**
     * Top-level status (typically {@code "ok"}).
     */
    private String status;

    /**
     * Raw JSON response from the exchange.
     */
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
}
