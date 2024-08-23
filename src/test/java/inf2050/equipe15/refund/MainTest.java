package inf2050.equipe15.refund;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {


    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    public void setStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }


    @Test
    public void out() {
        System.out.print("test output");
        assertEquals("test output", out.toString());
    }

    @Test
    public void testMainWithTooManyArgument() {
        String[] args = {"-X -Y -Z"};
        Main.main(args);
        String text = System.lineSeparator() +
                "Pour utiliser le programme, veuillez utiliser une des commandes suivantes:"+ System.lineSeparator() +
                "Pour afficher les statistique: Refund.java -S"+ System.lineSeparator() +
                "Pour réinitialiser les statistique: Refund.java -SR" + System.lineSeparator() +
                "Pour calculer des réclamations: Refund.java inputfile.json outputfile.json" + System.lineSeparator();

        assertEquals(text, out.toString());
    }
    @Test
    public void testMainWithNoArguments() {
        String[] args = {};
        Main.main(args);
        String text = System.lineSeparator() +
                "Pour utiliser le programme, veuillez utiliser une des commandes suivantes:"+ System.lineSeparator() +
                "Pour afficher les statistique: Refund.java -S"+ System.lineSeparator() +
                "Pour réinitialiser les statistique: Refund.java -SR" + System.lineSeparator() +
                "Pour calculer des réclamations: Refund.java inputfile.json outputfile.json" + System.lineSeparator();
        assertEquals(text, out.toString());
    }

    @Test
    public void testMainWithOneArgumentForResetStatistics() {
        String[] args = {"-SR"};
        Main.main(args);
        String text = "Les statistiques ont été réinitialisées!" + System.lineSeparator();
        assertEquals(text, out.toString());
    }

    @Test
    public void testMainWithOneInvalidArgument() {
        String[] args = {"-X"};
        Main.main(args);
        String text = System.lineSeparator() +
                "Pour utiliser le programme, veuillez utiliser une des commandes suivantes:"+ System.lineSeparator() +
                "Pour afficher les statistique: Refund.java -S"+ System.lineSeparator() +
                "Pour réinitialiser les statistique: Refund.java -SR" + System.lineSeparator() +
                "Pour calculer des réclamations: Refund.java inputfile.json outputfile.json" + System.lineSeparator();
        assertEquals(text, out.toString());
    }

}