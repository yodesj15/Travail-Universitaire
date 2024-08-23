package inf2050.equipe15.refund;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Validation {
    /**
     * This method validates the JSON data of the input file passed in argument.
     * It first validates the client's data and then validates all the claims data.
     *
     * @param jsonObject The JSON object that contains the claims data.
     */
    public static void validateJsonData(JsonObject jsonObject) throws InvalidJsonDataException {
        if(jsonObject == null){
            throw new InvalidJsonDataException("Fichier vide");
        }else {
            validateClientJsonData(jsonObject);
            validateAllClaimsJsonData(jsonObject);
        }
    }


    /**
     * This method validates all the claims data in the given JSON object.
     * It retrieves the claims data as a JSON array from the JSON object and iterates over each claim.
     * For each claim, it validates the claim data using the validateAClaimJsonData method.
     *
     * @param jsonObj The JSON object that contains the claims data.
     */
    private static void validateAllClaimsJsonData(JsonObject jsonObj) throws InvalidJsonDataException {
        if (jsonObj.get("reclamations").isJsonNull()){
            throw new InvalidJsonDataException("Reclamtions manquantes");
        }else {
            JsonArray jsonClaims = jsonObj.getAsJsonArray("reclamations");
            for (int i = 0; i < jsonClaims.size(); i++) {
                validateAClaimJsonData(jsonClaims.get(i).getAsJsonObject(), jsonObj);
            }
        }
    }

    /**
     * This method validates a single claim's JSON data in the given JSON object.
     * It checks if the claim JSON object has exactly 3 key-value pairs.
     * It validates the care number, date, and amount of the claim using the careNumber, isValidDate, and dollarSign
     * methods respectively.
     *
     * @param jsonClaim The JSON object that contains a single claim's data.
     * @param jsonObj The JSON object that contains the claims data.
     */
    private static void validateAClaimJsonData (JsonObject jsonClaim, JsonObject jsonObj ) throws InvalidJsonDataException {
        validateClaimType(jsonClaim);

        Validation.careNumber(jsonClaim.get("soin").getAsString());
        Validation.isValidDate(jsonClaim.get("date").getAsString(), jsonObj.get("mois").getAsString());
        Validation.validateAmount(jsonClaim.get("montant").getAsString());
    }


    /**
     * This method validate if the jsonClaim data are not null or an jsonArray
     *
     * @param jsonClaim The JSON object that contains a single claim's data.
     */
    private static void validateClaimType(JsonObject jsonClaim) throws InvalidJsonDataException{
        if (jsonClaim.get("soin").isJsonNull() || jsonClaim.get("soin").isJsonArray()) {
            throw new InvalidJsonDataException(jsonClaim.get("soin").isJsonNull() ? "Numero de soin manquant" :
                    "Type du numero de soin invalde (doit uniquement etre un entier)");
        } else if (jsonClaim.get("date").isJsonNull() || jsonClaim.get("date").isJsonArray()) {
            throw new InvalidJsonDataException(jsonClaim.get("date").isJsonNull() ? "Date de reclamation manquante" :
                    "Type de la date de reclamation invalde");
        } else if (jsonClaim.get("montant").isJsonNull() || jsonClaim.get("montant").isJsonArray()) {
            throw new InvalidJsonDataException(jsonClaim.get("montant").isJsonNull() ? "Montant reclamé manquant" :
                    "Type du montant reclamé invalide");
        }
    }


    /**
     * This method validates the client's JSON data in the given JSON object.
     * It validates the client number, contract letter, and the month using the clientNumber, contractLetter,
     * and validMonth methods respectively.
     *
     * @param jsonObj The JSON object that contains the claims data.
     */
    private static void validateClientJsonData(JsonObject jsonObj) throws InvalidJsonDataException {
        validateClientJsonDataType(jsonObj);

        Validation.ValidateFolderId(jsonObj.get("dossier").getAsString());
        Validation.validateMonth(jsonObj.get("mois").getAsString());
    }

    /**
     * This method validate if the ClientJson data are not null or an jsonArray
     *
     * @param jsonObj The JSON object that contains the claims data.
     */
    private static void validateClientJsonDataType(JsonObject jsonObj) throws InvalidJsonDataException{
        if (jsonObj.get("dossier").isJsonNull() || jsonObj.get("dossier").isJsonArray()){
            throw new InvalidJsonDataException(jsonObj.get("dossier").isJsonNull() ? "Numero de dosier manquant" :
                    "Type du numero de dossier invalide");
        } else if (jsonObj.get("mois").isJsonNull() || jsonObj.get("mois").isJsonArray()) {
            throw new InvalidJsonDataException(jsonObj.get("mois").isJsonNull() ? "Mois de traitement manquant" :
                    "Type du mois de traitement invalide");
        }
    }


    /**
     * Check if the folder Id is in the valid format
     *
     * @param Id The folder Id
     * @throws InvalidJsonDataException Send an invalid folder Id message
     */
    private static void ValidateFolderId(String Id) throws InvalidJsonDataException {
        if (!Id.matches("^[A-E]\\d{6}")) {
            throw new InvalidJsonDataException("Identifiant du dossier invalide.");
        }
    }


    /**
     * Check if the care number is a valid number
     *
     * @param numberString The care number
     * @throws InvalidJsonDataException Send an invalid number message
     */
     private static void careNumber(String numberString) throws InvalidJsonDataException {
        List<Integer> validCareNumber = List.of(0, 100, 150, 175, 200, 300, 400, 500, 600, 700);
        if (isNumberSequence(numberString)){
            int number = Integer.parseInt(numberString);
            if (!(number >= 300 && number <= 400) && !validCareNumber.contains(number)){
                throw new InvalidJsonDataException("Numéro de soin invalide.");
            }
        }else {
            throw new InvalidJsonDataException("Numéro de soin invalide.");
        }
    }


    /**
     * Check if the char is a number between 0 and 9
     *
     * @param character Take a random char
     * @return The boolean
     */
    public static boolean isNumber (char character) {
        return character >= '0' && character <= '9';
    }


    /**
     * Check if the number of client is composed only of number between 0 and 9
     *
     * @param numClient The number of client
     * @return The boolean
     */
    public static boolean isNumberSequence (String numClient) {
        for (int i = 0; i < numClient.length(); i++) {
            char character = numClient.charAt(i);
            if (!isNumber(character)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Check if reclamationDate is for the same month as current reclamation
     *
     * @param reclamationDate The full date of the reclamation
     * @param monthProcessed The specific month of the  current reclamation
     * @throws InvalidJsonDataException Send an invalid reclamationDate message
     */
    private static void isValidDate (String reclamationDate, String monthProcessed) throws InvalidJsonDataException {
        String reclamationMonth;
        try {
            LocalDate.parse(reclamationDate, DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (Exception e) {
            throw new InvalidJsonDataException("Date de reclamation invalide.");
        }
        reclamationMonth = reclamationDate.substring(0, 7);
        if (!reclamationMonth.equals(monthProcessed)){
            throw new InvalidJsonDataException("Date de reclamation invalide.");
        }
    }


    /**
     * Checks the format of the month processed with a regular expression which
     * verifies that the parameter entered begins with 4 digits followed
     * by a hyphen then a number in 01 and 12
     *
     * @param monthProcessed The specific month of the  current reclamation
     * @throws InvalidJsonDataException Send an invalid monthProcessed message
     */
    private static void validateMonth (String monthProcessed) throws InvalidJsonDataException {
        if (!monthProcessed.matches("^\\d{4}-(0[1-9]|1[0-2])$")){
            throw new InvalidJsonDataException("Mois traité invalide.");
        }
    }


    /**
     * Checks the format of the amount spent by the client with a regular expression which
     * verifies that the entered parameter begins with a series of numbers
     * followed by a period or coma then 2 numbers then the dollar sign
     *
     * @param amount The amount spent by the client
     * @throws InvalidJsonDataException Send an invalid amount format message
     */
    private static void validateAmount (String amount) throws InvalidJsonDataException {
        if (!amount.matches("^\\d+[.,]\\d{2}\\$$")) {
            throw new InvalidJsonDataException("Format du montant invalide.");
        }
    }
}