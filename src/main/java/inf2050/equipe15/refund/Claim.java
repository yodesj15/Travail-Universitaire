package inf2050.equipe15.refund;

import com.google.gson.JsonObject;

public class Claim {
    public Integer careNum;
    public String date;
    public Money amount;

    /**
     * Instantiates a new Claim and init all attributes.
     *
     * @param jsonClaim the json claim
     */
    public Claim(JsonObject jsonClaim) {
        this.careNum = jsonClaim.get("soin").getAsInt();
        this.date = jsonClaim.get("date").getAsString();
        this.amount = new Money(jsonClaim.get("montant").getAsString());
    }
}
