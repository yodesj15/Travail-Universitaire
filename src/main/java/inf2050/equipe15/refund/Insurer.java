package inf2050.equipe15.refund;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Insurer {
    protected Client newClient;
    protected JsonObject contractDataObject;

    private List<Refund> listRefund = new ArrayList<>();

    /**
     * Returns the list of refunds
     * @return the list of refunds
     */
    public List<Refund> getListRefund() {
        return listRefund;
    }

    /**
     * Instantiates a new Insurer. And get the contract data
     * from the json file contracts.json

     * @param newClient the new client
     */
    public Insurer(Client newClient) {
        this.newClient = newClient;
        this.contractDataObject = getContractsJsonObject();
    }

    /**
     * Multiple claims process that estimate the refund amount.
     * And add the refund to the list of refund
     *
     * @param claimByClient All the claim asked by client
     */
    public void claimProcess(ArrayList<Claim> claimByClient) {
        for (Claim claim : claimByClient) {
            Integer realCareNum = claim.careNum;
            Integer correctedCareNum = realCareNum >= 300 && realCareNum <= 399 ? 300 : realCareNum;

            Integer pourcCovered =  findPourcCoveredContract(correctedCareNum);
            Integer limitContract = findLimitContract(correctedCareNum);

            Money monthlyLimit = findMonthlyAmountLimit(correctedCareNum);
            Money coveredAmount = claim.amount.calculateCoveringAmount(pourcCovered,limitContract);

            if(monthlyAmountExceeded(realCareNum, monthlyLimit, coveredAmount)){
                int amountLeft = Math.abs(calculateTotalRefundOfGivenCareNum(realCareNum).amountCents - monthlyLimit.amountCents);
                int correctedLimit = new Money(amountLeft,'.').amountCents;
                coveredAmount = correctedLimit == 0  ? new Money() : claim.amount.calculateCoveringAmount(pourcCovered,correctedLimit);
            }
            this.listRefund.add(new Refund(realCareNum, claim.date, coveredAmount));
        }
    }

    /**
     * Used to know if the monthly amount limit is exceeded
     *
     * @param careNum the care number of the contract
     */
    private boolean monthlyAmountExceeded(Integer careNum, Money monthlyLimit, Money amountCovered)
    {
        return monthlyLimit.amountCents != 0 &&
                (Money.addition(calculateTotalRefundOfGivenCareNum(careNum),amountCovered)) > monthlyLimit.amountCents;
    }

    /**
     * Used to find the monthly amount limit covered by the care
     *
     * @param careNum the care number of the contract
     */
    private Money findMonthlyAmountLimit(Integer careNum) {
        String monthlyLimit = String.valueOf(this.contractDataObject.get(careNum.toString()).getAsJsonArray().get(0).getAsJsonObject().get("monthlyAmountLimit"));
        return new Money(monthlyLimit);
    }

    /**
     * Used to find the pourcentage covered by the contract
     *
     * @param careNum the care number of the contract
     */
    private Integer findPourcCoveredContract(Integer careNum) {
        String pourc = String.valueOf(this.contractDataObject.get(careNum.toString()).getAsJsonArray().get(0).getAsJsonObject().get( this.newClient.contract));
        return Integer.parseInt(pourc);
    }

    /**
     * Used to find the amount limit by the contract
     *
     * @param careNum the care number of the contract
     */
    private Integer findLimitContract(Integer careNum) {
        String limit = String.valueOf(this.contractDataObject.get(careNum.toString()).getAsJsonArray().get(0).getAsJsonObject().get( "limit" +this.newClient.contract));

        return Objects.equals(limit, "null") ? 0 : new Money(limit).amountCents;
    }

    /**
     * Used to calculate the total refund amount
     *
     * @return  The object Money containing the total refund amount
     */
    private Money calculateTotalRefund() {
        Integer total = 0;
        for (Refund refund : this.listRefund) {
            total += refund.amount.amountCents;
        }

        return new Money(total, '.');
    }

    /**
     * Used to calculate the total refund amount of a given care number
     *
     * @return  The object Money containing the total refund amount of a given care number
     */
    private Money calculateTotalRefundOfGivenCareNum(Integer careNum) {
        Integer total = 0;
        for (Refund refund : this.listRefund) {
            if(refund.careNum.equals(careNum)){
            total += refund.amount.amountCents;
            }
        }

        return new Money(total, '.');
    }

    /**
     * Function that write all refunds in the outputFile.json
     */
    public void produceRefundsReceipt() {
        JsonObject data = new JsonObject();
        data.addProperty("dossier", this.newClient.folder);
        data.addProperty("mois", this.newClient.date);

        JsonArray refundArray = new JsonArray();

        for (Refund refund :  this.listRefund) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("soin", refund.careNum);
            jsonObject.addProperty("date", refund.date);
            jsonObject.addProperty("montant", String.valueOf(refund.amount));
            refundArray.add(jsonObject);
        }
        data.add("remboursements", refundArray);
        data.addProperty("total", calculateTotalRefund().toString());

        String jsonString = new Gson().toJson(data);
        JsonInputFilesManager.writeJsonStringToOutput(jsonString);
    }

    /**
     * Function that return JsonObject of the contracts.json containing all elements
     */
    private JsonObject getContractsJsonObject() throws NullPointerException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("contracts.json");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        Gson gson = new Gson();
        return gson.fromJson(bufferedReader, JsonObject.class);
    }
}
