package io.github.hyperliquid.sdk.model.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Subscription to a user's live TWAP order states. Pushes
 * {@code {dex, user, states:[[twapId, TwapState], ...]}} on every update --
 * see {@code io.github.hyperliquid.sdk.model.info.TwapHistoryEntry.State} for
 * the {@code TwapState} shape (same fields, this push just omits the
 * wrapping {@code status}/{@code time} that {@code twapHistory} adds).
 */
public class TwapStatesSubscription extends Subscription {

    @JsonProperty("type")
    private final String type = "twapStates";

    @JsonProperty("user")
    private String user;

    @JsonProperty("dex")
    private String dex = "";

    public TwapStatesSubscription(String user) {
        this.user = user;
    }

    public TwapStatesSubscription(String user, String dex) {
        this.user = user;
        this.dex = dex == null ? "" : dex;
    }

    public static TwapStatesSubscription of(String user) {
        return new TwapStatesSubscription(user);
    }

    public static TwapStatesSubscription of(String user, String dex) {
        return new TwapStatesSubscription(user, dex);
    }

    @Override
    public String getType() {
        return type;
    }

    /**
     * The base {@link Subscription#toIdentifier()} default returns just
     * {@link #getType()} -- fine for single-user process-wide subscriptions
     * like most others in this package, but wrong here: without user (+dex)
     * baked in, two subscriptions for different users/dexes would collide
     * under the same identifier and silently overwrite each other's
     * registration/dedup entry.
     */
    @Override
    public String toIdentifier() {
        return dex == null || dex.isEmpty() ? type + ":" + user : type + ":" + user + ":" + dex;
    }
}
