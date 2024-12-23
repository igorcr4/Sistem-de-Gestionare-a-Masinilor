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
        if(id == null || id < 0 || !id.toString().matches("\\d+")) {
            throw new CarExceptions.FindCarException("Acest id este invalid!");
        }
    }

    public void validateKm(Integer km) {
        if(km == null || km < 0 || !km.toString().matches("\\d+")) {
            throw new CarExceptions.FindCarException("Introdu o valoare valida!");
        }
    }

    public void validateDate(String date) {
        if(date == null || date.isBlank()) {
            throw new CarExceptions.InformationUpdateException("Data introdusă nu poate fi nulă!");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parseDate = LocalDate.parse(date, formatter);

            if(parseDate.isBefore(LocalDate.now())) {
                throw new CarExceptions.InformationUpdateException("Data introdusa trebuie sa fie in prezent sau viitor!");
            }

        }catch (DateTimeParseException e) {
            throw new CarExceptions.InformationUpdateException("Data introdusă este invalidă! Formatul corect este dd/MM/yyyy!");
        }
    }

    public void validateCost(String cost) {
        if (cost == null || cost.trim().isEmpty()) {
            throw new CarExceptions.InformationUpdateException("Costul nu poate fi gol!");
        }

        try {
            BigDecimal validatedCost = new BigDecimal(cost);

            if (validatedCost.compareTo(BigDecimal.ZERO) <= 0) {
                throw new CarExceptions.InformationUpdateException("Costul trebuie să fie un număr pozitiv!");
            }
        } catch (NumberFormatException e) {
            throw new CarExceptions.InformationUpdateException("Costul introdus nu este un număr valid!");
        }
    }


    public boolean isCarNameInvalid(String name) {
        return name == null || !name.matches("^[a-zA-Z\\s-]+$");
    }

    public boolean isCarYearInvalid(String year) {
        return year == null || !year.matches("\\d{4}") || (Integer.parseInt(year) < 2000 || Integer.parseInt(year) > LocalDate.now().getYear());
    }

    public boolean isLicensePlateValidAndExists(String licensePlate) {
        return licensePlate == null || !repository.existsByLicensePlate(licensePlate) || !licensePlate.matches(licensePlateRegex);
    }
}
