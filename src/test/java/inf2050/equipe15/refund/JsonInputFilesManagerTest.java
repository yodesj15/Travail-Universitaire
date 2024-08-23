package inf2050.equipe15.refund;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JsonInputFilesManagerTest {
    private JsonInputFilesManager manager;
    @BeforeEach
    void setUp() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dossier", "A123456");
        jsonObject.addProperty("mois", "2021-01");

        JsonArray reclamations = new JsonArray();
        JsonObject claimObject = new JsonObject();
        claimObject.addProperty("soin", 100);
        claimObject.addProperty("date", "2021-01-01");
        claimObject.addProperty("montant", "100.00$");
        reclamations.add(claimObject);

        jsonObject.add("reclamations", reclamations);

        try {
            File tempFile = File.createTempFile("tempFile", ".json");
            tempFile.deleteOnExit();


            FileWriter writer = new FileWriter(tempFile);
            writer.write(jsonObject.toString());
            writer.close();

            manager = new JsonInputFilesManager(tempFile.getAbsolutePath());
        } catch (InvalidJsonDataException | IOException e) {
            fail("Invalid JSON data: " + e.getMessage());
        }
    }

    @Test
    void writeJsonStringToOutputShouldWriteJsonString() throws Exception {
        JsonInputFilesManager.setOutputFile("output.json");
        JsonInputFilesManager.writeJsonStringToOutput("{\"message\":\"test\"}");

        BufferedReader reader = new BufferedReader(new FileReader("output.json"));
        String actual = reader.lines().collect(Collectors.joining()).replaceAll("\\s", "");
        reader.close();

        String expected = "{\"message\":\"test\"}";

        assertEquals(expected, actual);
    }

    @Test
    void writeErrorJsonToOutputShouldWriteErrorMessage() throws Exception {
        JsonInputFilesManager.setOutputFile("output.json");
        JsonInputFilesManager.writeSpecificErrorJsonToOutput("error");

        BufferedReader reader = new BufferedReader(new FileReader("output.json"));
        String actual = reader.lines().collect(Collectors.joining()).replaceAll("\\s", "");
        reader.close();

        String expected = "{\"message\":\"error\"}";

        assertEquals(expected, actual);
    }

    @Test
    void createListClaimFromJsonShouldCreateListOfClaims() {
        JsonArray jsonArray = new JsonArray();
        JsonObject claimObject = new JsonObject();
        claimObject.addProperty("soin", 100);
        claimObject.addProperty("date", "2021-01-01");
        claimObject.addProperty("montant", "100.00$");
        jsonArray.add(claimObject);

        ArrayList<Claim> claims = JsonInputFilesManager.createListClaimFromJson(jsonArray);

        assertEquals(1, claims.size());
        assertEquals(100, claims.get(0).careNum);
        assertEquals("2021-01-01", claims.get(0).date);
        assertEquals("100.00$", claims.get(0).amount.toString());
    }

    @Test
    void getJsonObjShouldReturnJsonObject() {
        JsonObject jsonObject = manager.getJsonObj();
        assertNotNull(jsonObject);
    }

    @Test
    void testCreateJsonObjectFromFileThrowsExceptionOnInvalidFile() {
        assertThrows(InvalidJsonDataException.class, () -> new JsonInputFilesManager("invalid/path/to/file.json"));
    }

    @Test
    void testWriteJsonStringToOutputCatchesException() {
        JsonInputFilesManager.setOutputFile("/invalid/path/to/output.json");
        JsonInputFilesManager.writeJsonStringToOutput("{\"message\":\"test\"}");
    }

    @Test
    void testMissingClaimFieldThrowsInvalidJsonDataException() {
        // Setup a JSON object with all necessary fields except one required claim field
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dossier", "A123456");
        jsonObject.addProperty("mois", "2021-01");

        JsonArray reclamations = new JsonArray();
        JsonObject claimObject = new JsonObject();
        // Omitting the "soin" field to trigger the exception

        claimObject.addProperty("date", "2021-01-01");
        claimObject.addProperty("montant", "100.00$");
        reclamations.add(claimObject);
        jsonObject.add("reclamations", reclamations);

        assertThrows(InvalidJsonDataException.class, () -> {
            // Attempt to write this JSON to a temp file and use it to create a manager instance
            File tempFile = File.createTempFile("tempJson", ".json");
            tempFile.deleteOnExit();
            FileWriter writer = new FileWriter(tempFile);
            writer.write(jsonObject.toString());
            writer.close();

            // This should throw an exception because the "soin" field is missing
            new JsonInputFilesManager(tempFile.getAbsolutePath());
        }, "La donnée « soin » de la réclamation #1 est manquante dans le fichier Json soumis!");
    }

    @Test
    void testReclamationsFieldNotArrayThrowsInvalidJsonDataException() {
        // Create a JSON object with a "reclamations" field that is not an array (using a string here)
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dossier", "A123456");
        jsonObject.addProperty("mois", "2021-01");
        jsonObject.addProperty("reclamations", "This should be an array");

        assertThrows(InvalidJsonDataException.class, () -> {
            // Write this JSON to a temp file and use it to create a manager instance
            File tempFile = File.createTempFile("tempJson", ".json");
            tempFile.deleteOnExit();
            FileWriter writer = new FileWriter(tempFile);
            writer.write(jsonObject.toString());
            writer.close();

            // This should throw an exception because "reclamations" is not a JSON array
            new JsonInputFilesManager(tempFile.getAbsolutePath());
        }, "La donnée « reclamations » doit être un array dans le fichier Json soumis!");
    }

    @Test
    void testMissingRequiredFieldThrowsInvalidJsonDataException() {
        // Create a JSON object missing the "dossier" field
        JsonObject jsonObject = new JsonObject();
        // jsonObject.addProperty("dossier", "A123456");  // Intentionally commented out to simulate the missing field
        jsonObject.addProperty("mois", "2021-01");

        assertThrows(InvalidJsonDataException.class, () -> {
            // Write this JSON to a temp file and use it to create a manager instance
            File tempFile = File.createTempFile("tempJson", ".json");
            tempFile.deleteOnExit();
            FileWriter writer = new FileWriter(tempFile);
            writer.write(jsonObject.toString());
            writer.close();

            // This should throw an exception because the "dossier" field is missing
            new JsonInputFilesManager(tempFile.getAbsolutePath());
        }, "La donnée « dossier » est manquante dans le fichier Json soumis!");
    }
}