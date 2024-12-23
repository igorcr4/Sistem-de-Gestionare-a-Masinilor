package Sistem.de.Gestionare.a.Masinilor.jpa.services;

public interface MaintenanceReport {
    void checkOilChange();

    void checkInsuranceDate();

    void sendTireChangeAlert();
}
