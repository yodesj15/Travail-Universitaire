package inf2050.equipe15.refund;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class Statistics {
    private final String STATS_FILE_LOCATION = setStatsFileDefaultLocation();
    private final File STATS_FILE = new File(STATS_FILE_LOCATION);
    private final JsonObject JSON_OBJ_CARES = new JsonObject();
    private final String KEY_TREATED_FILES = "treated";
    private final String KEY_REJECTED_FILES = "rejected";
    private final String KEY_CARES = "cares";

    /**
     * Creates a string representing the default filepath location for the
     * statistics file.
     * @return a string representing the default filepath location.
     */
    protected String setStatsFileDefaultLocation() {
        String osName = System.getProperty("os.name");
        if (osName != null) {
            osName = (osName.toLowerCase().contains("win")
                    ? System.getenv("APPDATA")
                    : System.getProperty("user.home"));
            return osName.replace('\\', '/') + "/.UQAM-INF2050-refund-stats";
        } else {
            return Paths.get("").toAbsolutePath() + "/.UQAM-INF2050-refund-stats";
        }
    }

    /**
     * Create a Json Object with default values for the statistics.
     * @return the JsonObject with default values.
     */
    protected JsonObject createDefaultStatsJsonObject() {
        JsonObject defaultDataJsonObj = new JsonObject();
        defaultDataJsonObj.addProperty(KEY_TREATED_FILES, 0);
        defaultDataJsonObj.addProperty(KEY_REJECTED_FILES, 0);
        defaultDataJsonObj.add(KEY_CARES, new JsonObject());
        return defaultDataJsonObj;
    }

    /**
     * Write, from a Json Object, data to the file containing the statistics.
     * @param jsonObject the Json Object to write
     */
    protected void writeStatsToDataFile(JsonObject jsonObject) {
        try {
            FileWriter writer = new FileWriter(STATS_FILE);
            writer.write(jsonObject.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the stats within the statistics file and create a Json Object from it.
     * @return a Json Object containing the statistics.
     */
    protected JsonObject readStatsFromDataFileAsJsonObject() {
        try {
            JsonObject jsonObject = new Gson().getAdapter(JsonElement.class).fromJson(new FileReader(STATS_FILE)).getAsJsonObject();
            if (verifyStatsJsonObject(jsonObject)) {
                return jsonObject;
            } else  {
                throw new Exception("Le fichier des statistiques est incorrectement écrit.");
            }
        } catch (Exception e) {
            resetStats();
            return createDefaultStatsJsonObject();
        }
    }

    protected boolean verifyStatsJsonObject(JsonObject jsonObjStats) {
        String[] keyStats = {KEY_TREATED_FILES, KEY_REJECTED_FILES, KEY_CARES};
        return jsonObjStats.keySet().containsAll(List.of(keyStats))
                && jsonObjStats.get(KEY_TREATED_FILES).isJsonPrimitive()
                && jsonObjStats.get(KEY_TREATED_FILES).getAsJsonPrimitive().isNumber()
                && jsonObjStats.get(KEY_REJECTED_FILES).isJsonPrimitive()
                && jsonObjStats.get(KEY_REJECTED_FILES).getAsJsonPrimitive().isNumber()
                && jsonObjStats.get(KEY_CARES).isJsonObject()
                && jsonObjStats.getAsJsonObject(KEY_CARES).keySet().stream().allMatch(key ->
                        (jsonObjStats.getAsJsonObject(KEY_CARES).get(key).isJsonPrimitive()
                        && jsonObjStats.getAsJsonObject(KEY_CARES).get(key).getAsJsonPrimitive().isNumber()));
    }

    /**
     * Reset the statistics of the program.
     */
    public void resetStats() {
        writeStatsToDataFile(createDefaultStatsJsonObject());
    }

    /**
     * Write to the console the stats of the program.
     * The stats being the number of completed and rejected claims and the
     * number of care that has been reimbursed.
     */
    public void presentStats() {
        JsonObject jsonObjStats = readStatsFromDataFileAsJsonObject();
        System.out.println("Nombre de réclamations traitées : " + jsonObjStats.get(KEY_TREATED_FILES).getAsString());
        System.out.println("Nombre de réclamations rejetées : " + jsonObjStats.get(KEY_REJECTED_FILES).getAsString());
        JsonObject jsonArraySoinsTraitees = jsonObjStats.get(KEY_CARES).getAsJsonObject();
        if (jsonArraySoinsTraitees.isEmpty()) {
            System.out.println("Aucun soin traité");
        } else {
            System.out.println("Nombre de soins traités");
            jsonArraySoinsTraitees.keySet().forEach(key -> System.out.println("#" + key + " : " + jsonArraySoinsTraitees.get(key)));
        }
    }

    /**
     * Increase by 1 the number of rejected claims in the statistics.
     */
    public void incrementRejectedClaim() {
        JsonObject jsonObjCurrentStats = readStatsFromDataFileAsJsonObject();
        jsonObjCurrentStats.addProperty(KEY_REJECTED_FILES, jsonObjCurrentStats.get(KEY_REJECTED_FILES).getAsInt() + 1);
        writeStatsToDataFile(jsonObjCurrentStats);
    }

    /**
     * Increase by 1 the number of treated claims and it's associated care numbers in the statistics.
     */
    public void incrementSuccessfulClaim() {
        JsonObject jsonObjCurrentStats = readStatsFromDataFileAsJsonObject();
        jsonObjCurrentStats.addProperty(KEY_TREATED_FILES, jsonObjCurrentStats.get(KEY_TREATED_FILES).getAsInt() + 1);
        if (!JSON_OBJ_CARES.isEmpty()) {
            JsonObject jsonObjNumberCares = jsonObjCurrentStats.get(KEY_CARES).getAsJsonObject();
            JSON_OBJ_CARES.keySet().forEach(key -> {
                if (jsonObjNumberCares.has(key)) {
                    jsonObjNumberCares.addProperty(key, jsonObjNumberCares.get(key).getAsInt() + JSON_OBJ_CARES.get(key).getAsInt());
                } else {
                    jsonObjNumberCares.addProperty(key, JSON_OBJ_CARES.get(key).getAsInt());
                }
            });
        }
        writeStatsToDataFile(jsonObjCurrentStats);
    }

    /**
     * Add a care number to be added in the statistics to count how many times it has been treated.
     * @param careNumber the care number to count.
     */
    public void addCareNumber(int careNumber) {
        if (JSON_OBJ_CARES.has(String.valueOf(careNumber))) {
            JSON_OBJ_CARES.addProperty(String.valueOf(careNumber), JSON_OBJ_CARES.get(String.valueOf(careNumber)).getAsInt() + 1);
        } else {
            JSON_OBJ_CARES.addProperty(String.valueOf(careNumber), 1);
        }
    }
}