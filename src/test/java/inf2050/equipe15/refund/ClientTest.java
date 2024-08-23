package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void testConstructorWithValidInput() {
        String folder = "A123456";
        String date = "2023-01-11";
        Client client = new Client(folder, date);

        assertEquals("A", client.contract);
        assertEquals("123456", client.number);
        assertEquals(folder, client.folder);
        assertEquals(date, client.date);
    }

    @Test
    void extractContractLetter() {
        String folder = "A123456";
        Client client = new Client(folder, "2023-01-11");

        String contractLetter = client.extractContractLetter(folder);

        assertEquals("A", contractLetter);
    }

    @Test
    void extractNumberOfFolder() {
        String folder = "A123456";
        Client client = new Client(folder, "2023-01-11");

        String number = client.extractNumberOfFolder(folder);

        assertEquals("123456", number);
    }

    @Test
    void extractNumberOfFolderReturnsNull() {
        String folder = "ABCDEF";
        Client client = new Client(folder, "2023-01-11");

        String number = client.extractNumberOfFolder(folder);

        assertNull(number);
    }

    @Test
    void makeClaim() {
        String folder = "A123456";
        String date = "2023-01-11";
        Client client = new Client(folder, date);
        ArrayList<Claim> claims = new ArrayList<>();

        class TestInsurer extends Insurer {
            boolean claimProcessCalled = false;
            boolean produceRefundsReceiptCalled = false;

            public TestInsurer(Client client) {
                super(client);
            }

            @Override
            public void claimProcess(ArrayList<Claim> newClaim) {
                claimProcessCalled = true;
            }

            @Override
            public void produceRefundsReceipt() {
                produceRefundsReceiptCalled = true;
            }
        }
        TestInsurer testInsurer = new TestInsurer(client);
        client.insurer = testInsurer;

        client.makeClaim(claims);

        assertTrue(testInsurer.claimProcessCalled);
        assertTrue(testInsurer.produceRefundsReceiptCalled);
    }
}