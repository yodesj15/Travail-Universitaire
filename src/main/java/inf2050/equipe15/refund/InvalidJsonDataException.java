package inf2050.equipe15.refund;

public class InvalidJsonDataException extends Exception{
    public InvalidJsonDataException() {
        super("File passed in argument contains invalid JSON data.");
    }
    public InvalidJsonDataException(String message) {
        super(message);
    }
}
