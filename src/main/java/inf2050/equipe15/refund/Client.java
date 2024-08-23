package inf2050.equipe15.refund;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    protected String number;
    protected String contract;
    protected Insurer insurer;
    protected String folder;

    protected String date;

    /**
     * Instantiates a new Client.
     *
     * @param folder folder of the client
     * @param date  the date of the claim
     */
    public Client(String folder,String date) {
        this.folder = folder;
        this.number = extractNumberOfFolder(folder);
        this.contract = extractContractLetter(folder);
        this.insurer = new Insurer(this);
        this.date = date;
    }

    /**
     * This method extracts the contract letter from the folder name.
     *
     * @param folder the folder name
     * @return the contract letter
     */
    public String extractContractLetter(String folder) {
        return folder.substring(0, 1);
    }

    /**
     * This method extracts the number from the folder name.
     *
     * @param folder the folder name
     * @return the number
     */
    public String extractNumberOfFolder(String folder) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(folder);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }



    /**
     * Claim asked by client.
     *
     * @param newClaim the new claim validated
     */
    public void makeClaim(ArrayList<Claim> newClaim) {
        insurer.claimProcess(newClaim);
        insurer.produceRefundsReceipt();
    }
}
