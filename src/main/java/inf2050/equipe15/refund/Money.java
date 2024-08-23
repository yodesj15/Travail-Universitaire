package inf2050.equipe15.refund;

public class Money {
    private Integer dollar;
    private Integer cent;
    private Character delimiter ;
    protected  String amountStr;
    protected  Integer amountCents;

    /**
     * Default constructor used to initialize an empty object(0)
     */
    public Money(){
        this.delimiter = '.';
        this.amountCents = 0;
        this.cent = 0;
        this.dollar = 0;
    }

    /**
     * Instantiates a new Money. Can be used to recreate a Money object
     * from a cents amount and a delimiter
     * @param centsAmount the cents amount
     * @param delimiter   the delimiter '.' or ','
     */
    public Money(Integer centsAmount, Character delimiter){
        this.delimiter = delimiter;
        this.amountCents = centsAmount;
        this.cent = this.amountCents % 100;
        this.dollar = (this.amountCents - this.cent) / 100;
    }

    /**
     * Instantiates a new Money from a string.
     *
     * @param cashAmountStr the cash amount str
     */
    public Money(String cashAmountStr){
        this.delimiter = cashAmountStr.contains(".") ? '.' : ',';
        String regex = this.delimiter.equals('.') ? "\\." : ",";

        cashAmountStr = removeDollarSign(cashAmountStr);

        String[] parts = cashAmountStr.split(regex);

        this.amountCents = Integer.parseInt(parts[0]) * 100 + Integer.parseInt(parts[1]);

        this.cent = this.amountCents % 100;

        this.dollar = (this.amountCents - this.cent) / 100;

        this.amountStr = toString();
    }

    /**
     * Calculate the amount covered by the contract based on the pourcentage covered and
     * a limit given.
     *
     * @param pourcCovered  the pourc covered by the insurer
     * @param amountLimit the limit given
     * @return the object Money containing the amount covered for the refunds
     */
    public Money calculateCoveringAmount(Integer pourcCovered,Integer amountLimit){
             int coveredAmountCents ;

            if(this.amountCents % 100 != 0){
             coveredAmountCents = (this.amountCents - ((this.amountCents * pourcCovered) % 100))/100;
            }
            else{
               coveredAmountCents = this.amountCents * pourcCovered/100;
            }

            Money coveredAmount = new Money(coveredAmountCents,'.');

            int coveredAmountFormated = coveredAmount.amountCents;
            if (amountLimit != 0 && coveredAmountFormated != 0) {
                if (coveredAmountFormated >= amountLimit) {
                    coveredAmount = new Money(amountLimit,'.');
                }
            }
            return coveredAmount;
    }

    /**
     * Used to calculate the total of two Money objects
     * @return the total amount in cents of the two Money objects
     */
    public static Integer addition(Money firstAmount,Money secondAmount){
        return firstAmount.amountCents + secondAmount.amountCents;
    }

    /**
     * Remove dollar sign in string
     * The format is already verified to be in the correct format.
     * Format: 123.45$ or 123,45$
     * @param amount amount of the claim
     */
    private String removeDollarSign(String amount)  {
        return amount.replace("$", "");
    }

    public String toString(){
        String cent = this.cent < 10 ? (this.cent + "0") : this.cent.toString();
        return this.dollar.toString() + this.delimiter.toString() + cent + "$";
    }
}
