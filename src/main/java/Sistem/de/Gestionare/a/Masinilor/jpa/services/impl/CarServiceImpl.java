package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.CarDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.CarService;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository repository;
    @Autowired
    CarValidation validation;

    String notFoundId = "Masina cu acest id nu exista!";

    public Car saveCar(Car car) {

        String name = car.getName();
        String year = car.getYear();
        String licensePlate = car.getLicensePlate();

        validation.validateName(name);

        validation.validateYear(year);

        validation.checkLicensePlateValidityAndExistence(licensePlate);

        return repository.save(car);
    }

    public Iterable<Car> saveAll(List<Car> cars){
        for(Car car : cars) {
            String name = car.getName();
            String year = car.getYear();
            String licensePlate = car.getLicensePlate();

            validation.validateName(name);

            validation.validateYear(year);

            validation.checkLicensePlateValidityAndExistence(licensePlate);
        }

        return repository.saveAll(cars);
    }


    public CarDTO findByLicensePlate(String licensePlate) {

        validation.validateLicensePlate(licensePlate);

        Car car = repository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new CarExceptions.FindCarException("Masina cu numarul de inmatriculare: " + licensePlate + " nu exista!"));


        String firstName = car.getDriver() != null ? car.getDriver().getFirstName() : "";
        String lastName = car.getDriver() != null ? car.getDriver().getLastName() : "";

        return new CarDTO(car.getId(), firstName, lastName, licensePlate);
    }

    public Car findById(Long carId) {
        validation.validateId(carId);

        return repository.findById(carId)
                         .orElseThrow(() -> new CarExceptions.FindCarException(notFoundId));
    }

    public Iterable<Car> getAllCars() {
        if(repository.findAll().isEmpty()) {
            throw new CarExceptions.FindCarException("Nu exista nici o masina!");
        }
        return repository.findAll();
    }

    public void deleteByLicensePlate(String licensePlate) {
        validation.validateLicensePlate(licensePlate);

        Car car = repository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new CarExceptions.FindCarException("Masina cu numarul de inmatriculare: " + licensePlate + " nu exista!"));

        repository.delete(car);
    }

    public void updateKm(Long carId, Integer newKm) {
        validation.validateId(carId);
        validation.validateKm(newKm);

        Car car = findById(carId);
        car.setCurrentKm(newKm);
        repository.save(car);
    }

    public void setOilChange(Long carId, Integer oilKm) {
        validation.validateId(carId);
        validation.validateKm(oilKm);

        Car car = findById(carId);
        car.setNextOilChangeKm(oilKm);
        repository.save(car);
    }

    public void setInsurance(Long carId, LocalDate date) {
        validation.validateId(carId);
        validation.validateFutureDate(date);

        Car car = findById(carId);
        car.setNextInsurance(date);
        repository.save(car);
    }


}
