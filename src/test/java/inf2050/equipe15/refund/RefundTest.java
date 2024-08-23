package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefundTest {
    @Test
    void getCareNum() {
        Refund refund = new Refund(100, "2023-01-11", new Money("100.00"));
        assertEquals(100, refund.careNum);
    }

    @Test
    void getDate() {
        Refund refund = new Refund(100, "2023-01-11", new Money("100.00"));
        assertEquals("2023-01-11", refund.date);
    }

    @Test
    void getAmount() {
        Refund refund = new Refund(100, "2023-01-11", new Money("100.00"));
        assertEquals(new Money("100.00").amountStr, refund.amount.amountStr);
    }
}