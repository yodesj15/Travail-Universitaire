package inf2050.equipe15.refund;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            runStatisticOption(args[0]);
        }
        else if (args.length == 2) {
            runRefundProgram(args[0], args[1]);
        } else {
            writeUsageMessage();
        }
    }

    /**
     * Runs a program depending on the given single argument for the application.
     * If the argument is "-S", run the "present application statistics".
     * If the argument is "-SR", run the "reset application statistics".
     * Anything else will present the usage message.
     * @param arg the string containing the command line argument.
     */
    private static void runStatisticOption(String arg) {
        if (Objects.equals(arg, "-S")) {
            new Statistics().presentStats();
        } else if (Objects.equals(arg, "-SR")) {
            new Statistics().resetStats();
            System.out.println("Les statistiques ont été réinitialisées!");
        } else {
            writeUsageMessage();
        }
    }

    /**
     * Runs the applications main function to calculate the refunds submitted in the input file
     * to the output file.
     * @param inputFile the input file in JSON format containing the client's info and claims.
     * @param outputFile the output file where the refunds will be written
     */
    private static void runRefundProgram(String inputFile, String outputFile) {
        if (!verifyFirstArgIsExistingJsonFile(outputFile)
                ||!verifySecondArgumentIsJsonFile(inputFile))  {
            System.out.println("Veuillez fournir deux fichiers \".json\" comme arguments.");
            writeUsageMessage();
        }
        JsonInputFilesManager.setOutputFile(outputFile);
        Statistics stats = new Statistics();
        try {
            JsonInputFilesManager jsonInputFilesManager = new JsonInputFilesManager(inputFile);
            JsonObject inputDataObject = jsonInputFilesManager.getJsonObj();
            Validation.validateJsonData(inputDataObject);
            Client client = new Client(inputDataObject.get("dossier").getAsString(),inputDataObject.get("mois").getAsString());
            ArrayList<Claim> listClaim = JsonInputFilesManager.createListClaimFromJson(inputDataObject.getAsJsonArray("reclamations"));
            client.makeClaim(listClaim);
            listClaim.forEach(claim -> stats.addCareNumber(claim.careNum));
            stats.incrementSuccessfulClaim();
        } catch (InvalidJsonDataException e) {
            JsonInputFilesManager.writeSpecificErrorJsonToOutput(e.getMessage());
            stats.incrementRejectedClaim();
        }
    }

    /**
     * Verifies if the first argument for the program is an existing Json file.
     * @param arg the first argument supplied to the program
     * @return a boolean indicating if the argument is an existing json file
     */
    private static boolean verifyFirstArgIsExistingJsonFile(String arg) {
        return arg.endsWith(".json") && new File(arg).isFile();
    }
    /**
     * Verifies if the second argument for the program is the name of a Json file and
     * if it does not exist, creates the file.
     * @param arg the second argument supplied to the program
     * @return a boolean indicating if the argument is an appropriate json file
     */
    private static boolean verifySecondArgumentIsJsonFile(String arg) {
        if (arg.endsWith(".json")) {
            File outputFile = new File(arg);
            if (!outputFile.exists()) {
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Writes a message to the console indicating how to use the program properly
     * and exits.
     */
    private static void writeUsageMessage() {
        System.out.println();
        System.out.println("Pour utiliser le programme, veuillez utiliser une des commandes suivantes:");
        System.out.println("Pour afficher les statistique: Refund.java -S");
        System.out.println("Pour réinitialiser les statistique: Refund.java -SR");
        System.out.println("Pour calculer des réclamations: Refund.java inputfile.json outputfile.json");
    }
}