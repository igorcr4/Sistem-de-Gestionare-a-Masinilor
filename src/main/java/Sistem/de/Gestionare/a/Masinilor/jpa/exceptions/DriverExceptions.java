package Sistem.de.Gestionare.a.Masinilor.jpa.exceptions;

import java.util.List;

public class DriverExceptions extends RuntimeException {

    public static class DriverCreateValidationException extends RuntimeException {
        public DriverCreateValidationException(List<String> errors) {
            // Fiecare eroare este pe o linie separată
            super(String.join("\n", errors));
        }
    }

    public static class FindDriverException extends RuntimeException {
        public FindDriverException(String error) {
            super(error);
        }
    }

}
