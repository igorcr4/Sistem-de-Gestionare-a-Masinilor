package Sistem.de.Gestionare.a.Masinilor.jpa.exceptions;

public class ServiceHistoryExceptions extends RuntimeException {

    public static class ServiceHistoryNotFoundException extends RuntimeException {
        public ServiceHistoryNotFoundException(String message) {
            super(message);
        }
    }


}
