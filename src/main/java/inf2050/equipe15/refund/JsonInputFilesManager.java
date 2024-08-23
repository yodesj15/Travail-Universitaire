package inf2050.equipe15.refund;

import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Class meant to contain the json files submitted to the program,
 * read json data from the input file and write to the output file.
 */
public class JsonInputFilesManager {
    private static String outputFile;
    private String inputFile;
    private JsonObject jsonObj = null;

    /**
     * Constructor of a file manager with a json file containing data.
     * @throws InvalidJsonDataException if inputFiled file is not a valid json file.
     */
    public JsonInputFilesManager(String inputFile) throws InvalidJsonDataException {
        this.inputFile = inputFile;
        this.jsonObj = createJsonObjectFromFile(this.inputFile);
        validateJsonInputContainsAllClientFields(this.jsonObj);
        validateJsonInputContainsAllClaimFields(this.jsonObj);
    }

    /**
     * Creates a json object from the inputed file.
     * @param inputfile the Json file that contains the to read.
     * @return the new json object created from the file.
     * @throws InvalidJsonDataException if inputFiled file is not a valid json file.
     */
    private JsonObject createJsonObjectFromFile(String inputfile) throws InvalidJsonDataException {
        JsonElement json;
        try {
            TypeAdapter<JsonElement> strictAdapter = new Gson().getAdapter(JsonElement.class);
            json = strictAdapter.fromJson(new FileReader(inputfile));
        } catch (Exception e) {
            throw new InvalidJsonDataException();
        }
        return json.getAsJsonObject();
    }

    /**
     * Validate if all the fields about the client actually exist in the jsonObject.
     * @param jsonObject the jsonObject to validate.
     * @throws InvalidJsonDataException if the jsonObject is missing at least one of the
     *                                  necessary fields.
     */
    private void validateJsonInputContainsAllClientFields(JsonObject jsonObject) throws InvalidJsonDataException {
        for (String field : InputJsonFileFields.getAllClientFields()) {
            if (!jsonObject.has(field)) {
                throw new InvalidJsonDataException("La donnée « " + field + " » est manquante dans le fichier Json soumis!");
            }
        }
    }

    /**
     * Validate if all the fields are valid for each claim in the JsonObject
     * @param jsonObject the JsonObject for which each claim must be validated
     * @throws InvalidJsonDataException if any of the fields is missing from the JsonObject's claims
     */
    private void validateJsonInputContainsAllClaimFields(JsonObject jsonObject) throws InvalidJsonDataException {
        if (jsonObject.get(InputJsonFileFields.RECLAMATIONS.toString()).isJsonArray()) {
            JsonArray jsonClaims = jsonObject.getAsJsonArray(InputJsonFileFields.RECLAMATIONS.toString());
            for (int i = 0; i < jsonClaims.size(); i++) {
                JsonObject jsonClaimObject = jsonClaims.get(i).getAsJsonObject();
                for (String field : InputJsonFileFields.getAllClaimFields()) {
                    if (!jsonClaimObject.has(field)) {
                        throw new InvalidJsonDataException("La donnée « " + field + " » de la réclamation #" + (i + 1) +
                                " est manquante dans le fichier Json soumis!");
                    }
                }
            }
        } else {
            throw new InvalidJsonDataException("La donnée « " + InputJsonFileFields.RECLAMATIONS +
                                                " » doit être un array dans le fichier Json soumis!");
        }
    }

    /**
     * Set the classe's output file.
     * @param outputFile A string representing the output file path.
     */
    public static void setOutputFile(String outputFile) {
        JsonInputFilesManager.outputFile = outputFile;
    }

    /**
     * Writes string in a json format to the output file.
     * @param jsonString A string in a json format.
     */
    public static void writeJsonStringToOutput(String jsonString){
        JsonElement jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de l'écriture vers le fichier de sortie.");
        }
    }

    /**
     * Write a specific error message in a json format to the output file
     * and ends the program.
     * @param errorMessage A string representing the error message.
     */
    public static void writeSpecificErrorJsonToOutput(String errorMessage) {
        writeJsonStringToOutput("{\"message\":\"" + errorMessage + "\"}");
    }

    /**
     * Create list claim from json array list.
     *
     * @param jsonArray the json array
     * @return the array list
     */
    public static ArrayList<Claim> createListClaimFromJson(JsonArray jsonArray) {
        ArrayList<Claim> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            list.add(new Claim(jsonElement.getAsJsonObject()));
        }
        return list;
    }

    /**
     * Gets the json object that was read from the input file.
     * @return A Json Object containing the data of the input file
     */
    public JsonObject getJsonObj() {
        return this.jsonObj;
    }
}
