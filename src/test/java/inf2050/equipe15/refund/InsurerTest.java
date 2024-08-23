package inf2050.equipe15.refund;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InsurerTest {
    private Insurer insurer;
    private Client client;
    @BeforeEach
    void setUp() {
        client = new Client("A123456", "2021-01");
        insurer = new Insurer(client);
    }

    @Test
    void insurerConstructor() {
        assertNotNull(insurer);
        assertEquals(client, insurer.newClient);
        assertNotNull(insurer.contractDataObject);
    }

    @Test
    void claimProcessWithValidClaims() {
        ArrayList<Claim> claims = new ArrayList<>();

        JsonObject claimObject1 = new JsonObject();
        claimObject1.addProperty("soin", 100);
        claimObject1.addProperty("date", "2021-01-01");
        claimObject1.addProperty("montant", "100.00$");
        claims.add(new Claim(claimObject1));

        JsonObject claimObject2 = new JsonObject();
        claimObject2.addProperty("soin", 200);
        claimObject2.addProperty("date", "2021-01-02");
        claimObject2.addProperty("montant", "200.00$");
        claims.add(new Claim(claimObject2));

        insurer.claimProcess(claims);

        Refund expectedRefund1 = new Refund(100, "2021-01-01", new Money("35.00"));
        Refund expectedRefund2 = new Refund(100, "2021-01-02", new Money("50.00"));
        assertEquals(2, insurer.getListRefund().size());
        assertEquals(expectedRefund1.amount.toString(), insurer.getListRefund().get(0).amount.toString());
        assertEquals(expectedRefund2.amount.toString(), insurer.getListRefund().get(1).amount.toString());
    }

    @Test
    void claimProcessWithNoClaims() {
        ArrayList<Claim> claims = new ArrayList<>();

        insurer.claimProcess(claims);
    }

    @Test
    void produceRefundsReceiptAfterProcessingClaims() {
        ArrayList<Claim> claims = new ArrayList<>();

        JsonObject claimObject1 = new JsonObject();
        claimObject1.addProperty("soin", 100);
        claimObject1.addProperty("date", "2021-01-01");
        claimObject1.addProperty("montant", "100.00$");
        claims.add(new Claim(claimObject1));

        JsonObject claimObject2 = new JsonObject();
        claimObject2.addProperty("soin", 200);
        claimObject2.addProperty("date", "2021-01-02");
        claimObject2.addProperty("montant", "200.00$");
        claims.add(new Claim(claimObject2));

        insurer.claimProcess(claims);

        Refund expectedRefund1 = new Refund(100, "2021-01-01", new Money("35.00"));
        Refund expectedRefund2 = new Refund(100, "2021-01-02", new Money("50.00"));
        assertEquals(expectedRefund1.amount.toString(), insurer.getListRefund().get(0).amount.toString());
        assertEquals(expectedRefund2.amount.toString(), insurer.getListRefund().get(1).amount.toString());

        insurer.produceRefundsReceipt();
    }

    @Test
    void makeClaimsWithAmountGreaterThanMonthlyMaximum() {
        ArrayList<Claim> claims = new ArrayList<>();

        JsonObject claimObject1 = new JsonObject();
        claimObject1.addProperty("soin", 200);
        claimObject1.addProperty("date", "2021-01-01");
        claimObject1.addProperty("montant", "1000.00$");
        claims.add(new Claim(claimObject1));

        JsonObject claimObject2 = new JsonObject();
        claimObject2.addProperty("soin", 200);
        claimObject2.addProperty("date", "2021-01-05");
        claimObject2.addProperty("montant", "200.00$");
        claims.add(new Claim(claimObject2));

        JsonObject claimObject3 = new JsonObject();
        claimObject3.addProperty("soin", 100);
        claimObject3.addProperty("date", "2021-01-10");
        claimObject3.addProperty("montant", "100.00$");
        claims.add(new Claim(claimObject3));

        insurer.claimProcess(claims);

        Refund expectedRefund1 = new Refund(200, "2021-01-02", new Money("250.00"));
        //Note: the monthly limit for contract A, care 200 is 250$, any refund after that is set to 0.00$
        Refund expectedRefund2 = new Refund(100, "2021-01-05", new Money("0.00"));
        Refund expectedRefund3 = new Refund(100, "2021-01-10", new Money("35.00"));

        assertEquals(expectedRefund1.amount.toString(), insurer.getListRefund().get(0).amount.toString());
        assertEquals(expectedRefund2.amount.toString(), insurer.getListRefund().get(1).amount.toString());
        assertEquals(expectedRefund3.amount.toString(), insurer.getListRefund().get(2).amount.toString());

    }

    @Test
    void produceRefundsReceiptWithoutProcessingClaims() {
        insurer.produceRefundsReceipt();
    }
}