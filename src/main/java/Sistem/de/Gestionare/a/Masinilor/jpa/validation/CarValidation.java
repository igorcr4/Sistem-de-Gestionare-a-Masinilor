package Sistem.de.Gestionare.a.Masinilor.jpa.validation;

public interface CarValidation {
    void validateId(Long id);

    void validateKm(Integer km);

    void validateDate(String date);

    void validateCost(String cost);

    boolean isCarNameInvalid(String name);

    boolean isCarYearInvalid(String year);

    boolean isLicensePlateValidAndExists(String licensePlate);
}
