package io.github.hyperliquid.sdk.model.order;

/**
 * Request to place a TWAP order via the {@code twapOrder} L1 action.
 * <p>
 * Unlike {@link OrderRequest}, there is no {@code cloid} slot on the wire for
 * TWAP orders -- the exchange assigns a {@code twapId} on acceptance, and
 * that id (not a client-supplied one) is the only handle for cancel/status.
 * </p>
 * <p>
 * {@code triggerPx}/{@code triggerAbove} and {@code stopPx} correspond to the
 * {@code details} object added to the {@code twapOrder} action in the
 * 2026-08-01 Hyperliquid protocol upgrade: {@code triggerPx} delays
 * activation until the mark price crosses it ({@code triggerAbove} selects
 * above/below), and {@code stopPx} terminates the TWAP once the mark price
 * reaches it. Both are optional; leave null for "start immediately, no price
 * protection".
 * </p>
 */
public class TwapOrderRequest {

    private InstrumentType instrumentType = InstrumentType.PERP;
    private String coin;
    private Boolean isBuy;
    private String sz;
    private Boolean reduceOnly = false;
    private Integer minutes;
    private Boolean randomize = false;
    private String triggerPx;
    private Boolean triggerAbove;
    private String stopPx;

    public TwapOrderRequest() {
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Boolean getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Boolean isBuy) {
        this.isBuy = isBuy;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public Boolean getReduceOnly() {
        return reduceOnly;
    }

    public void setReduceOnly(Boolean reduceOnly) {
        this.reduceOnly = reduceOnly;
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

    public String getTriggerPx() {
        return triggerPx;
    }

    public void setTriggerPx(String triggerPx) {
        this.triggerPx = triggerPx;
    }

    public Boolean getTriggerAbove() {
        return triggerAbove;
    }

    public void setTriggerAbove(Boolean triggerAbove) {
        this.triggerAbove = triggerAbove;
    }

    public String getStopPx() {
        return stopPx;
    }

    public void setStopPx(String stopPx) {
        this.stopPx = stopPx;
    }

    /**
     * Convenience factory for the common case: immediate start (no trigger),
     * optional stop/termination price, no randomization.
     */
    public static TwapOrderRequest of(String coin, boolean isBuy, String sz, int minutes, String stopPx) {
        TwapOrderRequest req = new TwapOrderRequest();
        req.setCoin(coin);
        req.setIsBuy(isBuy);
        req.setSz(sz);
        req.setMinutes(minutes);
        req.setStopPx(stopPx);
        return req;
    }

    @Override
    public String toString() {
        return "TwapOrderRequest{" +
                "instrumentType=" + instrumentType +
                ", coin='" + coin + '\'' +
                ", isBuy=" + isBuy +
                ", sz='" + sz + '\'' +
                ", reduceOnly=" + reduceOnly +
                ", minutes=" + minutes +
                ", randomize=" + randomize +
                ", triggerPx='" + triggerPx + '\'' +
                ", triggerAbove=" + triggerAbove +
                ", stopPx='" + stopPx + '\'' +
                '}';
    }
}
