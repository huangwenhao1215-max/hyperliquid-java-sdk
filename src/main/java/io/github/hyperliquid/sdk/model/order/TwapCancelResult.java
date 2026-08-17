package io.github.hyperliquid.sdk.model.order;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response model for {@link io.github.hyperliquid.sdk.apis.Exchange#twapCancel(String, long)}.
 */
public class TwapCancelResult {

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

    public boolean isSuccess() {
        return response != null && response.data != null && response.data.isSuccess();
    }

    public String getError() {
        return response == null || response.data == null ? null : response.data.getError();
    }

    @Override
    public String toString() {
        return "TwapCancelResult{status='" + status + "', response=" + response + '}';
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
         * {@code status} is either the plain string {@code "success"} or an
         * object {@code {"error":"msg"}} -- kept as a raw {@link JsonNode}
         * for the same reason as {@link TwapOrder.Response.Data#getStatus()}.
         */
        public static class Data {

            private JsonNode status;

            public JsonNode getStatus() {
                return status;
            }

            public void setStatus(JsonNode status) {
                this.status = status;
            }

            public boolean isSuccess() {
                return status != null && status.isTextual() && "success".equals(status.asText());
            }

            public String getError() {
                if (status == null || !status.isObject()) return null;
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
