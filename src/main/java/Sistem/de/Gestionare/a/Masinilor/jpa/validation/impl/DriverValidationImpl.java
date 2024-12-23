package Sistem.de.Gestionare.a.Masinilor.jpa.validation.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.repository.DriverRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.DriverValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverValidationImpl implements DriverValidation {
    @Autowired
    DriverRepository repository;

    String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    String nameRegex = "^[A-Z][a-z]+([\\s-][A-Z][a-z]+)*$";

    public boolean isFirstNameInvalid(String firstName) {
        return firstName == null || !firstName.matches(nameRegex);
    }

    public boolean isLastNameInvalid(String lastName) {
        return lastName == null || !lastName.matches(nameRegex);
    }

    public boolean isCnpInvalid(String cnp) {
        return cnp == null || repository.existsByCnp(cnp) || !cnp.matches(nameRegex);
    }

    public boolean isNumberInvalid(String number) {
        return number == null || repository.existsByPhoneNumber(number) || !number.matches("\\d{10}");
    }

    public boolean isEmailInvalid(String email) {
        return email == null || repository.existsByEmail(email) || !email.matches(emailRegex);
    }



}
