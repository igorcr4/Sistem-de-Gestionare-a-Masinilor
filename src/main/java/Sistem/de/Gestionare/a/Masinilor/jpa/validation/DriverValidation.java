package Sistem.de.Gestionare.a.Masinilor.jpa.validation;

public interface DriverValidation {
    boolean isFirstNameInvalid(String firstName);

    boolean isLastNameInvalid(String lastName);

    boolean isCnpInvalid(String cnp);

    boolean isNumberInvalid(String number);

    boolean isEmailInvalid(String email);
}
