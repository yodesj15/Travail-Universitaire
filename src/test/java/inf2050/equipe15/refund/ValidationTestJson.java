package inf2050.equipe15.refund;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTestJson {

    private JsonObject validJsonObject;
    private JsonArray jsonArrayClaims;
    private JsonObject jsonObjectClaim;

    @BeforeEach
    void setUp() {
        validJsonObject = new JsonObject();
        validJsonObject.addProperty("dossier", "A123456");
        validJsonObject.addProperty("mois", "2023-01");

        jsonArrayClaims = new JsonArray();
        jsonObjectClaim = new JsonObject();
        jsonObjectClaim.addProperty("soin", "100");
        jsonObjectClaim.addProperty("date", "2023-01-11");
        jsonObjectClaim.addProperty("montant", "100.00$");
        jsonArrayClaims.add(jsonObjectClaim);

        validJsonObject.add("reclamations", jsonArrayClaims);
    }

    @Test
    void validateJsonData_WithValidJson() {
        assertDoesNotThrow(() -> Validation.validateJsonData(validJsonObject));
    }

    @Test
    void validateJsonData_NullDossier_ThrowsException() {
        validJsonObject.addProperty("dossier", (String) null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Numero de dosier manquant", exception.getMessage());
    }

    @Test
    void validateJsonData_DossierAsArray_ThrowsException() {
        validJsonObject.add("dossier", new JsonArray());

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Type du numero de dossier invalide", exception.getMessage());
    }

    @Test
    void validateJsonData_NullMois_ThrowsException() {
        validJsonObject.addProperty("mois", (String) null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Mois de traitement manquant", exception.getMessage());
    }

    @Test
    void validateJsonData_MoisAsArray_ThrowsException() {
        validJsonObject.add("mois", new JsonArray());

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Type du mois de traitement invalide", exception.getMessage());
    }

    @Test
    void testNullJsonObject(){
        JsonObject nullJsonObject = null;
        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(nullJsonObject));
    }

    @Test
    void validateJsonData_WithValidClaimData() {
        assertDoesNotThrow(() -> Validation.validateJsonData(validJsonObject));
    }

    @Test
    void validateJsonData_NullSoin_ThrowsException() {
        jsonObjectClaim.add("soin", null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Numero de soin manquant", exception.getMessage());
    }

    @Test
    void validateJsonData_SoinAsArray_ThrowsException() {
        jsonObjectClaim.add("soin", new JsonArray());

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Type du numero de soin invalde (doit uniquement etre un entier)", exception.getMessage());
    }

    @Test
    void validateJsonData_NullDate_ThrowsException() {
        jsonObjectClaim.add("date", null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Date de reclamation manquante", exception.getMessage());
    }

    @Test
    void validateJsonData_DateAsArray_ThrowsException() {
        jsonObjectClaim.add("date", new JsonArray());

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Type de la date de reclamation invalde", exception.getMessage());
    }

    @Test
    void validateJsonData_NullMontant_ThrowsException() {
        jsonObjectClaim.add("montant", null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Montant reclamé manquant", exception.getMessage());
    }

    @Test
    void validateJsonData_MontantAsArray_ThrowsException() {
        jsonObjectClaim.add("montant", new JsonArray());

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Type du montant reclamé invalide", exception.getMessage());
    }

    @Test
    void validateJsonData_MissingReclamations_ThrowsException() {
        validJsonObject.add("reclamations", null);

        Exception exception = assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(validJsonObject));
        assertEquals("Reclamtions manquantes", exception.getMessage());
    }

}