package Sistem.de.Gestionare.a.Masinilor.jpa.exceptions;

import java.util.List;

public class DriverExceptions extends RuntimeException {

    public static class ValidationException extends RuntimeException {
        public ValidationException(String error) {
            super(error);
        }
    }

    public static class FindException extends RuntimeException {
        public FindException(String error) {
            super(error);
        }
    }

}
