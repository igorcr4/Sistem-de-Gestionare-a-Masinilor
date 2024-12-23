package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.DriverDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.DriverExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Driver;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.DriverRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.DriverService;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.CarValidation;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.DriverValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository repository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarValidation validation;
    @Autowired
    private DriverValidation driverValidation;

    String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    String nameRegex = "^[A-Z][a-z]+([\\s-][A-Z][a-z]+)*$";


    String notFoundId = "Sofer cu acest ID nu exista!";

    String notFoundCarId = "Masina cu acest ID nu exista!";

    public Driver saveDriver(Driver driver) {
        List<String> errors = new ArrayList<>();

        String firstName = driver.getFirstName();
        String lastName = driver.getLastName();
        String cnp = driver.getCnp();
        String number = driver.getPhoneNumber();
        String email = driver.getEmail();

        if(driverValidation.isFirstNameInvalid(firstName)) {
            errors.add("Prenumele: " + firstName + " este invalid!");
        }

        if(driverValidation.isLastNameInvalid(lastName)) {
            errors.add("Numele: " + lastName + " este invalid!");
        }

        if (driverValidation.isCnpInvalid(cnp)) {
            errors.add("CNP invalid sau deja existent!");
        }

        if (driverValidation.isNumberInvalid(number)) {
            errors.add("Numar de telefon invalid sau deja existent!");
        }

        if (driverValidation.isEmailInvalid(email)) {
            errors.add("Email invalid sau deja existent pentru!");
        }

        if(!errors.isEmpty()) {
            throw new DriverExceptions.DriverCreateValidationException(errors);
        }

        return repository.save(driver);
    }

    public Iterable<Driver> saveAll(List<Driver> drivers){
        List<String> errors = new ArrayList<>();

        for(Driver dr : drivers) {
            String firstName = dr.getFirstName();
            String lastName = dr.getLastName();
            String cnp = dr.getCnp();
            String number = dr.getPhoneNumber();
            String email = dr.getEmail();

            if(driverValidation.isFirstNameInvalid(firstName)) {
                errors.add("Prenumele: " + firstName + " este invalid!");
            }

            if(driverValidation.isLastNameInvalid(lastName)) {
                errors.add("Numele: " + lastName + " este invalid!");
            }

            if (driverValidation.isCnpInvalid(cnp)) {
                errors.add("CNP invalid sau deja existent!");
            }

            if (driverValidation.isNumberInvalid(number)) {
                errors.add("Numar de telefon invalid sau deja existent!");
            }

            if (driverValidation.isEmailInvalid(email)) {
                errors.add("Email invalid sau deja existent pentru!");
            }
        }

        if(!errors.isEmpty()) {
            throw new DriverExceptions.DriverCreateValidationException(errors);
        }

       return repository.saveAll(drivers);
    }

    public List<DriverDTO> findByFullName(String firstName, String lastName) {
        if(driverValidation.isFirstNameInvalid(firstName)) {
            throw new DriverExceptions.FindDriverException("Prenume invalid!");
        }

        if(driverValidation.isLastNameInvalid(lastName)) {
            throw new DriverExceptions.FindDriverException("Nume invalid!");
        }

        List<Driver> drivers = repository.findByFullName(firstName, lastName);

        if(drivers.isEmpty()) {
           throw new DriverExceptions.FindDriverException("Acest sofer nu exista sau numele este invalid!");
        }

        return drivers.stream().map(driver -> {
            String licensePlate = driver.getCar() != null ? driver.getCar().getLicensePlate() : "";
            return new DriverDTO(driver.getId(), driver.getFirstName(), driver.getLastName(), licensePlate);
        }).toList();
    }

    public Driver findById(Long id) {
        validation.validateId(id);

        return repository.findById(id)
                .orElseThrow(() -> new DriverExceptions.FindDriverException(notFoundId));
    }

    public Iterable<Driver> getAllDrivers(){

        if(repository.findAll().isEmpty()) {
            throw new DriverExceptions.FindDriverException("Nu exista nici un sofer!");
        }
        return repository.findAll();
    }

    public void deleteDriver(Long id){
        validation.validateId(id);

        repository.findById(id)
                  .orElseThrow(() -> new DriverExceptions.FindDriverException(notFoundId));

        repository.deleteById(id);
    }


    public Driver mapDriverToCar(Long driverId, Long carId) {
        validation.validateId(driverId);

        Driver driver = repository.findById(driverId)
                .orElseThrow(() -> new DriverExceptions.FindDriverException(notFoundId));

        validation.validateId(carId);

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarExceptions.FindCarException(notFoundCarId));

        driver.setCar(car);
        return driver;
    }


}
