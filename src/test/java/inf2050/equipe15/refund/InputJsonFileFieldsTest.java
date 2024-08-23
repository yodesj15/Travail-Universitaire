package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputJsonFileFieldsTest {
    @Test
    void getAllClientFields() {
        String[] expectedFields = { "dossier", "mois", "reclamations" };
        assertArrayEquals(expectedFields, InputJsonFileFields.getAllClientFields());
    }

    @Test
    void getAllClaimFields() {
        String[] expectedFields = { "soin", "date", "montant" };
        assertArrayEquals(expectedFields, InputJsonFileFields.getAllClaimFields());
    }

    @Test
    void testToString() {
        assertEquals("dossier", InputJsonFileFields.DOSSIER.toString());
        assertEquals("mois", InputJsonFileFields.MOIS.toString());
        assertEquals("reclamations", InputJsonFileFields.RECLAMATIONS.toString());
        assertEquals("soin", InputJsonFileFields.SOIN.toString());
        assertEquals("date", InputJsonFileFields.DATE.toString());
        assertEquals("montant", InputJsonFileFields.MONTANT.toString());
    }
}