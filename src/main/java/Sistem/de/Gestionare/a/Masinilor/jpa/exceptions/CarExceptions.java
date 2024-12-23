package Sistem.de.Gestionare.a.Masinilor.jpa.exceptions;

import java.util.List;

public class CarExceptions extends RuntimeException {

    public static class CarCreateValidationException extends RuntimeException {
        public CarCreateValidationException(List<String> errors) {
            super(String.join("\n", errors));
        }
    }

    public static class FindCarException extends RuntimeException {
        public FindCarException(String error) {
            super(error);
        }
    }

    public static class InformationUpdateException extends RuntimeException {
        public InformationUpdateException(String error) {
            super(error);
        }
    }


}
