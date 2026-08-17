package io.github.hyperliquid.sdk.model.info;

/**
 * One entry of {@link io.github.hyperliquid.sdk.apis.Info#twapHistory(String)}'s
 * response array -- a user's TWAP order, past or present, with its current
 * status and cumulative execution state. Unlike {@link OrderStatus} (single
 * order, queried by oid), this endpoint always returns the user's full TWAP
 * list; callers filter by {@link #getTwapId()} themselves.
 */
public class TwapHistoryEntry {

    /** Creation time of this history record, in seconds since epoch. */
    private Long time;

    private Long twapId;

    private Status status;

    private State state;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTwapId() {
        return twapId;
    }

    public void setTwapId(Long twapId) {
        this.twapId = twapId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TwapHistoryEntry{" +
                "time=" + time +
                ", twapId=" + twapId +
                ", status=" + status +
                ", state=" + state +
                '}';
    }

    /**
     * {@code status.status} in {@code "finished"/"activated"/"terminated"/
     * "waitingForTrigger"/"stopped"/"error"}; {@code description} is only
     * present when {@code status=="error"}.
     */
    public static class Status {

        private String status;
        private String description;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        /** {@code true} once the TWAP will never produce further fills. */
        public boolean isTerminal() {
            return "finished".equals(status) || "terminated".equals(status)
                    || "stopped".equals(status) || "error".equals(status);
        }

        @Override
        public String toString() {
            return "Status{status='" + status + "', description='" + description + "'}";
        }
    }

    public static class State {

        private String coin;
        private String executedNtl;
        private String executedSz;
        private Integer minutes;
        private Boolean randomize;
        private Boolean reduceOnly;
        private String side;
        private String stopPx;
        private String sz;
        private Long timestamp;
        private Trigger trigger;
        private String user;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getExecutedNtl() {
            return executedNtl;
        }

        public void setExecutedNtl(String executedNtl) {
            this.executedNtl = executedNtl;
        }

        public String getExecutedSz() {
            return executedSz;
        }

        public void setExecutedSz(String executedSz) {
            this.executedSz = executedSz;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public void setMinutes(Integer minutes) {
            this.minutes = minutes;
        }

        public Boolean getRandomize() {
            return randomize;
        }

        public void setRandomize(Boolean randomize) {
            this.randomize = randomize;
        }

        public Boolean getReduceOnly() {
            return reduceOnly;
        }

        public void setReduceOnly(Boolean reduceOnly) {
            this.reduceOnly = reduceOnly;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getStopPx() {
            return stopPx;
        }

        public void setStopPx(String stopPx) {
            this.stopPx = stopPx;
        }

        public String getSz() {
            return sz;
        }

        public void setSz(String sz) {
            this.sz = sz;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Trigger getTrigger() {
            return trigger;
        }

        public void setTrigger(Trigger trigger) {
            this.trigger = trigger;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "State{" +
                    "coin='" + coin + '\'' +
                    ", executedNtl='" + executedNtl + '\'' +
                    ", executedSz='" + executedSz + '\'' +
                    ", minutes=" + minutes +
                    ", randomize=" + randomize +
                    ", reduceOnly=" + reduceOnly +
                    ", side='" + side + '\'' +
                    ", stopPx='" + stopPx + '\'' +
                    ", sz='" + sz + '\'' +
                    ", timestamp=" + timestamp +
                    ", trigger=" + trigger +
                    ", user='" + user + '\'' +
                    '}';
        }

        public static class Trigger {

            private String px;
            private Boolean above;

            public String getPx() {
                return px;
            }

            public void setPx(String px) {
                this.px = px;
            }

            public Boolean getAbove() {
                return above;
            }

            public void setAbove(Boolean above) {
                this.above = above;
            }

            @Override
            public String toString() {
                return "Trigger{px='" + px + "', above=" + above + '}';
            }
        }
    }
}
