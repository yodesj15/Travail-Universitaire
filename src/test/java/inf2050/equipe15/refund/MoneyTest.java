package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void calculateCoveringAmount() {
        Money money = new Money(25000, '.');
        Money coveredAmount = money.calculateCoveringAmount(50, 15000);

        assertTrue(coveredAmount.amountCents <= 15000);
    }

    @Test
    void calculateCoveringAmountWithNonMultipleAmount() {
        Money money = new Money(25050, '.');
        Money coveredAmount = money.calculateCoveringAmount(100, 20000);

        assertTrue(coveredAmount.amountCents <= 20000);
    }

    @Test
    void testToString() {
        Money money = new Money(12345, '.');
        String moneyStr = money.toString();

        assertEquals("123.45$", moneyStr);
    }

    @Test
    void testCoveredAmountExceedsLimit() {
        Money money = new Money(30000, '.');
        Money coveredAmount = money.calculateCoveringAmount(100, 20000);

        assertEquals(20000, coveredAmount.amountCents);
    }

    @Test
    public void testDefaultConstructor() {
        Money money = new Money();
        assertEquals("0.00$", money.toString());
    }

    @Test
    public void testConstructorWithCents() {
        Money money = new Money(12345, '.');
        assertEquals("123.45$", money.toString());
        money = new Money(678, ',');
        assertEquals("6,78$", money.toString());
    }

    @Test
    public void testConstructorFromString() {
        Money money = new Money("123.45$");
        assertEquals("123.45$", money.toString());
        money = new Money("678,90$");
        assertEquals("678,90$", money.toString());
    }

    @Test
    public void testCalculateCoveringAmount() {
        Money baseAmount = new Money(10000, '.'); // 100.00$
        Money covered = baseAmount.calculateCoveringAmount(50, 5000); // 50%, $50 limit
        assertEquals("50.00$", covered.toString());

        covered = baseAmount.calculateCoveringAmount(25, 1000); // 25%, $10 limit
        assertEquals("10.00$", covered.toString());
    }

    @Test
    public void testAddition() {
        Money firstAmount = new Money("10.00$");
        Money secondAmount = new Money("20.00$");
        Integer totalCents = Money.addition(firstAmount, secondAmount);
        Money totalMoney = new Money(totalCents, '.');
        assertEquals("30.00$", totalMoney.toString());
    }

}