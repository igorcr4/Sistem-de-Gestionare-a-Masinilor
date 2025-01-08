package Sistem.de.Gestionare.a.Masinilor.jpa.validation;

public interface DriverValidation {

    void validateName(String firstName);

    void validateLastName(String lastName);

    void validateCnp(String cnp);

    void validatePhoneNumber(String number);

    void validateEmail(String email);
}
