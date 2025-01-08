package Sistem.de.Gestionare.a.Masinilor.jpa.validation;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CarValidation {
    void validateId(Long id);

    void validateKm(Integer km);

    void validateFutureDate(LocalDate date);

    void validatePastDate(LocalDate date);

    void validateCost(BigDecimal cost);

    void validateName(String name);

    void validateYear(String year);

    void validateLicensePlate(String licensePlate);

    void checkLicensePlateValidityAndExistence(String licensePlate);
}
