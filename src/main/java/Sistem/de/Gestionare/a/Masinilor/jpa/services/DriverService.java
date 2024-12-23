package Sistem.de.Gestionare.a.Masinilor.jpa.services;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.DriverDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Driver;

import java.util.List;

public interface DriverService {
    Driver saveDriver(Driver driver);

    Iterable<Driver> saveAll(List<Driver> drivers);

    List<DriverDTO> findByFullName(String firstName, String lastName);

    Driver findById(Long id);

    Iterable<Driver> getAllDrivers();

    void deleteDriver(Long id);

    Driver mapDriverToCar(Long driverId, Long carId);
}
