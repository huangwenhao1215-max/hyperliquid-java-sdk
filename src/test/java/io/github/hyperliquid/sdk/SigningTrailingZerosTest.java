package io.github.hyperliquid.sdk;

import io.github.hyperliquid.sdk.utils.Signing;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The backend hashes "p"/"s" string fields in canonical form (trailing zeros
 * stripped), so writeMsgpack must normalize them before packing -- otherwise
 * the signature recovers to a random address ("User or API Wallet ... does not
 * exist"). See https://github.com/nktkas/hyperliquid UnsignedDecimal transform
 * and the npm "hyperliquid" package's normalizeTrailingZeros for reference.
 */
public class SigningTrailingZerosTest {

    private static Map<String, Object> twapAction(String sz) {
        Map<String, Object> twap = new LinkedHashMap<>();
        twap.put("a", 4);
        twap.put("b", true);
        twap.put("s", sz);
        twap.put("r", false);
        twap.put("m", 19);
        twap.put("t", false);
        Map<String, Object> action = new LinkedHashMap<>();
        action.put("type", "twapOrder");
        action.put("twap", twap);
        return action;
    }

    @Test
    public void twapHashIgnoresTrailingZeros() {
        byte[] h1 = Signing.actionHash(twapAction("2.0000"), 1755432100123L, null, null);
        byte[] h2 = Signing.actionHash(twapAction("2"), 1755432100123L, null, null);
        assertArrayEquals(h2, h1);
    }

    @Test
    public void stopPxDetailsHashIgnoresTrailingZeros() {
        Map<String, Object> a1 = twapAction("2");
        Map<String, Object> details1 = new LinkedHashMap<>();
        details1.put("s", "1900.0");
        a1.put("details", details1);

        Map<String, Object> a2 = twapAction("2");
        Map<String, Object> details2 = new LinkedHashMap<>();
        details2.put("s", "1900");
        a2.put("details", details2);

        assertArrayEquals(Signing.actionHash(a2, 1L, null, null), Signing.actionHash(a1, 1L, null, null));
    }

    @Test
    public void fractionalSizesNormalizeCorrectly() {
        byte[] h1 = Signing.actionHash(twapAction("0.500"), 7L, null, null);
        byte[] h2 = Signing.actionHash(twapAction("0.5"), 7L, null, null);
        assertArrayEquals(h2, h1);
    }

    @Test
    public void plainOrderHashUnchangedForCanonicalValues() {
        // Regular orders already emit floatToWire-canonical strings, so normalization
        // must be a no-op there: same bytes as an explicitly canonical action.
        Map<String, Object> order1 = new LinkedHashMap<>();
        order1.put("a", 4);
        order1.put("b", true);
        order1.put("p", "1900");
        order1.put("s", "2");
        order1.put("r", false);
        order1.put("t", "limit");
        Map<String, Object> a1 = new LinkedHashMap<>();
        a1.put("type", "order");
        a1.put("orders", java.util.List.of(order1));
        a1.put("grouping", "normal");

        Map<String, Object> order2 = new LinkedHashMap<>(order1);
        order2.put("p", "1900.00000000");
        order2.put("s", "2.0");
        Map<String, Object> a2 = new LinkedHashMap<>();
        a2.put("type", "order");
        a2.put("orders", java.util.List.of(order2));
        a2.put("grouping", "normal");

        assertArrayEquals(Signing.actionHash(a1, 42L, null, null), Signing.actionHash(a2, 42L, null, null));
    }

    @Test
    public void removeTrailingZerosBasics() {
        assertEquals("2", Signing.removeTrailingZeros("2.0000"));
        assertEquals("0.5", Signing.removeTrailingZeros("0.500"));
        assertEquals("1900", Signing.removeTrailingZeros("1900.0"));
        assertEquals("2", Signing.removeTrailingZeros("2"));
        assertEquals("100", Signing.removeTrailingZeros("100"));
        assertEquals("0", Signing.removeTrailingZeros("0.0"));
        assertEquals("1.05", Signing.removeTrailingZeros("1.050"));
    }
}
