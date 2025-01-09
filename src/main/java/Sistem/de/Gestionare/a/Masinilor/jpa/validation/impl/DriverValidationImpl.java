package Sistem.de.Gestionare.a.Masinilor.jpa.validation.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.DriverExceptions;
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

    String cnpRegex = "^[1-8]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{6}$";


    public void validateName(String firstName) {
        if(firstName == null || !firstName.matches(nameRegex)) {
            throw new DriverExceptions.ValidationException("Prenumele " + firstName + " este invalid!");
        }
    }

    public void validateLastName(String lastName) {
        if(lastName == null || !lastName.matches(nameRegex)) {
            throw new DriverExceptions.ValidationException("Numele " + lastName + " este invalid!");
        }
    }

    public void validateCnp(String cnp) {
        if(cnp == null || repository.existsByCnp(cnp) || !cnp.matches(cnpRegex)) {
            throw new DriverExceptions.ValidationException("CNP: " + cnp + " este invalid sau deja exista!");
        }
    }

    public void validateAge(Integer age) {
        if(age == null) {
            throw new DriverExceptions.ValidationException("Introduceti o varsta!");
        }

        if(age < 18) {
            throw new DriverExceptions.ValidationException("Soferul trebuie să aiba cel putin 18 ani!");
        }
    }

    public void validatePhoneNumber(String number) {
        if(number == null || repository.existsByPhoneNumber(number) || !number.matches("\\d{10}")) {
            throw new DriverExceptions.ValidationException("Numarul: " + number + " este invalid sau deja exista!");
        }
    }

    public void validateEmail(String email) {
        if(email == null || repository.existsByEmail(email) || !email.matches(emailRegex)) {
            throw new DriverExceptions.ValidationException("Email: " + email + " este invalid sau deja exista!");
        }
    }



}
