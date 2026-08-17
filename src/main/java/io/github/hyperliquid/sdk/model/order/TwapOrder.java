package io.github.hyperliquid.sdk.model.order;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response model for {@link io.github.hyperliquid.sdk.apis.Exchange#twapOrder(TwapOrderRequest)}.
 */
public class TwapOrder {

    /**
     * Top-level status string from the exchange (e.g. {@code "ok"} or {@code "err"}).
     */
    private String status;

    private Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * @return the assigned TWAP id, or {@code null} if the request failed
     */
    public Long getTwapId() {
        return response == null || response.data == null ? null : response.data.getTwapId();
    }

    /**
     * @return the error message, or {@code null} if the request succeeded
     */
    public String getError() {
        return response == null || response.data == null ? null : response.data.getError();
    }

    @Override
    public String toString() {
        return "TwapOrder{status='" + status + "', response=" + response + '}';
    }

    public static class Response {

        private String type;
        private Data data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Response{type='" + type + "', data=" + data + '}';
        }

        /**
         * {@code status} is a discriminated union on the wire --
         * {@code {"running":{"twapId":N}}} on success, {@code {"error":"msg"}} on
         * failure -- kept as a raw {@link JsonNode} rather than forced into one
         * shape, same treatment {@link Cancel.Response.Data} gives its own
         * polymorphic {@code statuses} field.
         */
        public static class Data {

            private JsonNode status;

            public JsonNode getStatus() {
                return status;
            }

            public void setStatus(JsonNode status) {
                this.status = status;
            }

            public Long getTwapId() {
                if (status == null) return null;
                JsonNode running = status.get("running");
                if (running == null) return null;
                JsonNode twapId = running.get("twapId");
                return twapId == null ? null : twapId.asLong();
            }

            public String getError() {
                if (status == null) return null;
                JsonNode error = status.get("error");
                return error == null ? null : error.asText();
            }

            @Override
            public String toString() {
                return "Data{status=" + status + '}';
            }
        }
    }
}
