package inf2050.equipe15.refund;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;

public class ValidationTest {


    static JsonObject jsonObj = new JsonObject();
    static JsonObject jsonObjectClaim = new JsonObject();

    @BeforeEach
    void setUp() {
        jsonObj.addProperty("dossier", "A123456");
        jsonObj.addProperty("mois", "2023-01");

        JsonArray jsonArrayClaims = new JsonArray();

        jsonObjectClaim.addProperty("soin", "100");
        jsonObjectClaim.addProperty("date", "2023-01-11");
        jsonObjectClaim.addProperty("montant", "100.00$");

        jsonArrayClaims.add(jsonObjectClaim);

        jsonObj.add("reclamations", jsonArrayClaims);
    }

    @Test
    void validateJsonData_ValidData() {
        assertDoesNotThrow(() -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidFolderData_ThrowsException() {
        jsonObj.remove("dossier");
        jsonObj.addProperty("dossier",  "F123456");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidFolderData2_ThrowsException() {
        jsonObj.remove("dossier");
        jsonObj.addProperty("dossier",  "A1234567");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidFolder3Data_ThrowsException() {
        jsonObj.remove("dossier");
        jsonObj.addProperty("dossier",  "Be23456");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidMonthData_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-13");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidMonth2Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-10-23");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidMonth3Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "202q3-09");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidMonth4Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-3");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidCareNumberData_ThrowsException() {
        jsonObjectClaim.remove("soin");
        jsonObjectClaim.addProperty("soin",  "10");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidCareNumber2Data_ThrowsException() {
        jsonObjectClaim.remove("soin");
        jsonObjectClaim.addProperty("soin",  "299");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidCareNumber3Data_ThrowsException() {
        jsonObjectClaim.remove("soin");
        jsonObjectClaim.addProperty("soin",  "401");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidCareNumber4Data_ThrowsException() {
        jsonObjectClaim.remove("soin");
        jsonObjectClaim.addProperty("soin",  "1001");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidCareNumber5Data_ThrowsException() {
        jsonObjectClaim.remove("soin");
        jsonObjectClaim.addProperty("soin",  "t99");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidDateData_ThrowsException() {
        jsonObjectClaim.remove("date");
        jsonObjectClaim.addProperty("date",  "2023-00-11");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidDate2Data_ThrowsException() {
        jsonObjectClaim.remove("date");
        jsonObjectClaim.addProperty("date",  "2023-01-32");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidDate3Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-12");

        jsonObjectClaim.remove("date");
        jsonObjectClaim.addProperty("date",  "2022-12-11");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidDate4Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-u2");

        jsonObjectClaim.remove("date");
        jsonObjectClaim.addProperty("date",  "2023-u2-11");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidDate5Data_ThrowsException() {
        jsonObj.remove("mois");
        jsonObj.addProperty("mois",  "2023-12");

        jsonObjectClaim.remove("date");
        jsonObjectClaim.addProperty("date",  "2023-12-78");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidAmountData_ThrowsException() {
        jsonObjectClaim.remove("montant");
        jsonObjectClaim.addProperty("montant",  "12.345$");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidAmount2Data_ThrowsException() {
        jsonObjectClaim.remove("montant");
        jsonObjectClaim.addProperty("montant",  "12.3$");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidAmount3Data_ThrowsException() {
        jsonObjectClaim.remove("montant");
        jsonObjectClaim.addProperty("montant",  "12,35");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }

    @Test
    void validateJsonData_InvalidAmount4Data_ThrowsException() {
        jsonObjectClaim.remove("montant");
        jsonObjectClaim.addProperty("montant",  "12.3e$");

        assertThrows(InvalidJsonDataException.class, () -> Validation.validateJsonData(jsonObj));
    }
}