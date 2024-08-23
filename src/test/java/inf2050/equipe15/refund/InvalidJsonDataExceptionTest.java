package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidJsonDataExceptionTest {
    @Test
    void testNoArgConstructor() {
        InvalidJsonDataException exception = new InvalidJsonDataException();
        assertEquals("File passed in argument contains invalid JSON data.", exception.getMessage());
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Test message";
        InvalidJsonDataException exception = new InvalidJsonDataException(message);
        assertEquals(message, exception.getMessage());
    }

}