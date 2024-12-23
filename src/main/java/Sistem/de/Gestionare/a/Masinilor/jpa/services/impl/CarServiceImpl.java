package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.CarDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.DriverExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.CarService;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository repository;
    @Autowired
    CarValidation validation;

    String notFoundId = "Masina cu acest id nu exista!";

    public Car saveCar(Car car) {
        List<String> errors = new ArrayList<>();

        String name = car.getName();
        String year = car.getYear();
        String licensePlate = car.getLicensePlate();

        if(validation.isCarNameInvalid(name)) {
            errors.add("Numele " + name + " este invalid!");
        }

        if(validation.isCarYearInvalid(year)) {
            errors.add("Anul " + year + " este invalid!");
        }

        if(validation.isLicensePlateValidAndExists(licensePlate)) {
            errors.add("Numarul " + licensePlate + " este invalid sau deja exista!");
        }
        if(!errors.isEmpty()) {
            throw new CarExceptions.CarCreateValidationException(errors);
        }

        return repository.save(car);
    }

    public Iterable<Car> saveAll(List<Car> cars){
        List<String> errors = new ArrayList<>();

        for(Car car : cars) {
            String name = car.getName();
            String year = car.getYear();
            String licensePlate = car.getLicensePlate();

            if(validation.isCarNameInvalid(name)) {
                errors.add("Numele " + name + " este invalid!");
            }

            if(validation.isCarYearInvalid(year)) {
                errors.add("Anul " + year + " este invalid!");
            }

            if(validation.isLicensePlateValidAndExists(licensePlate)) {
                errors.add("Numarul " + licensePlate + " este invalid sau deja exista!");
            }
        }

        if(!errors.isEmpty()) {
            throw new DriverExceptions.DriverCreateValidationException(errors);
        }

        return repository.saveAll(cars);
    }


    public CarDTO findByLicensePlate(String licensePlate) {
        Car car = repository.findByLicensePlate(licensePlate);

        if(validation.isLicensePlateValidAndExists(licensePlate)) {
            throw new CarExceptions.FindCarException("Numarul de inmatriculare este invalid sau masina nu exista!");
        }

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
            throw new DriverExceptions.FindDriverException("Nu exista nici o masina!");
        }
        return repository.findAll();
    }

    public void deleteByLicensePlate(String licensePlate) {

        if(validation.isLicensePlateValidAndExists(licensePlate)) {
            throw new CarExceptions.FindCarException("Numarul de inmatriculare este invalid sau nu exista!");
        }

        Car car = repository.findByLicensePlate(licensePlate);

        repository.delete(car);
    }

    public void updateKm(Long carId, Integer newKm) {
        validation.validateId(carId);
        validation.validateKm(newKm);

        Car car = findById(carId);
        car.setCurrentKm(newKm);
    }

    public void setOilChange(Long carId, Integer oilKm) {
        validation.validateId(carId);
        validation.validateKm(oilKm);

        Car car = findById(carId);
        car.setNextOilChangeKm(oilKm);
    }

    public void setInsurance(Long carId, String date) {
        validation.validateId(carId);
        validation.validateDate(date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nextInsurance = LocalDate.parse(date, formatter);

        Car car = findById(carId);
        car.setNextInsurance(nextInsurance);
    }


}
