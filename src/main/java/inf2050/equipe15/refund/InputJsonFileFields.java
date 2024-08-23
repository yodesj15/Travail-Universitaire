package inf2050.equipe15.refund;

public enum InputJsonFileFields {
    DOSSIER,
    MOIS,
    RECLAMATIONS,
    SOIN,
    DATE,
    MONTANT;

    /**
     * Get all the field names that are necessary in the inputted ".json" file
     * to an array of strings.
     * @return An array of string of the necessary Json fields
     */
    public static String[] getAllClientFields() {
        return new String[] {DOSSIER.toString(), MOIS.toString(), RECLAMATIONS.toString()};
    }

    /**
     * Get all the field names that are necessary in the inputted ".json" file
     * for each claim ("reclamations").
     * @return An array of string of the necessary Json fields for each claim.
     */
    public static String[] getAllClaimFields() {
        return new String[] {SOIN.toString(), DATE.toString(), MONTANT.toString()};
    }

    /**
     * Returns the name of the Enum constant as a lowercase string.
     * @return The name of the Enum constant in lowercase form
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
