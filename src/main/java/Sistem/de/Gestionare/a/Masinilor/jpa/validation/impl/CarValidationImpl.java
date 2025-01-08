package Sistem.de.Gestionare.a.Masinilor.jpa.validation.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CarValidationImpl implements CarValidation {
    @Autowired
    CarRepository repository;

    String licensePlateRegex = "^[A-Z]{1,2}\\s\\d{1,3}\\s[A-Z]{3}$";


    public void validateId(Long id) {
        if(id == null || id <= 0) {
            throw new CarExceptions.InformationValidityException("Acest id este invalid!");
        }

    }

    public void validateKm(Integer km) {
        if(km == null || km <= 0) {
            throw new CarExceptions.FindCarException("Valoarea introdusă pentru kilometraj nu este validă!");
        }
    }

    public void validateFutureDate(LocalDate date) {
        if (date == null) {
            throw new CarExceptions.InformationUpdateException("Data introdusă nu poate fi nulă!");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new CarExceptions.InformationUpdateException("Data introdusă trebuie să fie în viitor!");
        }
    }

    public void validatePastDate(LocalDate date) {
        if (date == null) {
            throw new CarExceptions.InformationUpdateException("Data introdusă nu poate fi nula!");
        }

        if (date.isAfter(LocalDate.now())) {
            throw new CarExceptions.InformationUpdateException("Data introdusa trebuie sa fie in prezent sau în trecut!");
        }
    }


    public void validateCost(BigDecimal cost) {
        if (cost == null) {
            throw new CarExceptions.InformationUpdateException("Costul trebuie să fie specificat!");
        }

        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CarExceptions.InformationUpdateException("Costul trebuie să fie un număr pozitiv!");
        }
    }

    public void validateName(String name) {
        if(name == null || !name.matches("^[a-zA-Z\\s-]+$")) {
            throw new CarExceptions.InformationValidityException("Nume invalid!");
        }
    }

    public void validateYear(String year) {
        if(year == null || !year.matches("\\d{4}") || (Integer.parseInt(year) < 2000 || Integer.parseInt(year) > LocalDate.now().getYear())) {
            throw new CarExceptions.InformationValidityException("An invalid!");
        }
    }

    public void validateLicensePlate(String licensePlate) {
        if(licensePlate == null || !licensePlate.matches(licensePlateRegex)) {
            throw new CarExceptions.FindCarException("Numar de inmatriculare: " + licensePlate + " este invalid!");
        }
    }

    public void checkLicensePlateValidityAndExistence(String licensePlate) {
        if(licensePlate == null || repository.existsByLicensePlate(licensePlate) || !licensePlate.matches(licensePlateRegex)) {
            throw new CarExceptions.FindCarException("Numar de inmatriculare: " + licensePlate + " este invalid sau deja exista!");
        }
    }


}
