package Sistem.de.Gestionare.a.Masinilor.jpa.services;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.CarDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarService {
    Car saveCar(Car car);

    Iterable<Car> saveAll(List<Car> cars);

    CarDTO findByLicensePlate(String licensePlate);

    Iterable<Car> getAllCars();

    Car findById(Long id);

    void deleteByLicensePlate(String licensePlate);

    void updateKm(Long carId, Integer newKm);

    void setOilChange(Long carId, Integer oilKm);

    void setInsurance(Long carId, LocalDate date);

}
