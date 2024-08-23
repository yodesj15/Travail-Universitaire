package inf2050.equipe15.refund;

public class Refund {
    public Integer careNum;
    public String date;
    public Money amount;

    /**
     * Instantiates a new Refund.
     *
     * @param careNum the care num
     * @param date    the date
     * @param amount  the amount
     */
    public Refund(Integer careNum, String date, Money amount) {
        this.careNum = careNum;
        this.date = date;
        this.amount = amount;
    }
}
