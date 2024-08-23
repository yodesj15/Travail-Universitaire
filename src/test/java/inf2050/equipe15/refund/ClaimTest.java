package inf2050.equipe15.refund;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClaimTest {
    @Test
    void testConstructorWithValidInput() {
        JsonObject jsonClaim = new JsonObject();
        jsonClaim.addProperty("soin", 100);
        jsonClaim.addProperty("date", "2023-01-11");
        jsonClaim.addProperty("montant", "100.00");

        Claim claim = new Claim(jsonClaim);

        assertEquals(100, claim.careNum);
        assertEquals("2023-01-11", claim.date);
        assertEquals("100.00$", claim.amount.toString());
    }

}