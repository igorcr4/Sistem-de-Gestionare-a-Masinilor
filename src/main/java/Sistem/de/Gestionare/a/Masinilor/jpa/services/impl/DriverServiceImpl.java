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

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarValidation validation;
    @Autowired
    private DriverValidation driverValidation;
    String notFoundId = "Sofer cu acest id nu exista!";

    String notFoundCarId = "Masina cu acest id nu exista!";

    public Driver saveDriver(Driver driver) {
        String firstName = driver.getFirstName();
        String lastName = driver.getLastName();
        String cnp = driver.getCnp();
        String number = driver.getPhoneNumber();
        String email = driver.getEmail();

       driverValidation.validateName(firstName);

       driverValidation.validateLastName(lastName);

       driverValidation.validateCnp(cnp);

       driverValidation.validatePhoneNumber(number);

        driverValidation.validateEmail(email);

        return driverRepository.save(driver);
    }

    public Iterable<Driver> saveAll(List<Driver> drivers){

        for(Driver dr : drivers) {
            String firstName = dr.getFirstName();
            String lastName = dr.getLastName();
            String cnp = dr.getCnp();
            String number = dr.getPhoneNumber();
            String email = dr.getEmail();

            driverValidation.validateName(firstName);

            driverValidation.validateLastName(lastName);

            driverValidation.validateCnp(cnp);

            driverValidation.validatePhoneNumber(number);

            driverValidation.validateEmail(email);
        }

       return driverRepository.saveAll(drivers);
    }

    public List<DriverDTO> findByFullName(String firstName, String lastName) {
       driverValidation.validateName(firstName);

       driverValidation.validateLastName(lastName);

        List<Driver> drivers = driverRepository.findByFullName(firstName, lastName);

        if(drivers.isEmpty()) {
           throw new DriverExceptions.FindException("Acest sofer nu exista sau numele este invalid!");
        }

        return drivers.stream().map(driver -> {
            String licensePlate = driver.getCar() != null ? driver.getCar().getLicensePlate() : "";
            return new DriverDTO(driver.getId(), driver.getFirstName(), driver.getLastName(), licensePlate);
        }).toList();
    }

    public Driver findById(Long id) {
        validation.validateId(id);

        return driverRepository.findById(id).orElseThrow(() -> new DriverExceptions.FindException(notFoundId));
    }

    public Iterable<Driver> getAllDrivers(){

        if(driverRepository.findAll().isEmpty()) {
            throw new DriverExceptions.FindException("Nu exista nici un sofer!");
        }
        return driverRepository.findAll();
    }

    public void deleteDriver(Long id){
        validation.validateId(id);

        driverRepository.findById(id).orElseThrow(() -> new DriverExceptions.FindException(notFoundId));

        driverRepository.deleteById(id);
    }


    public Driver mapDriverToCar(Long driverId, Long carId) {
        validation.validateId(driverId);
        validation.validateId(carId);

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverExceptions.FindException(notFoundId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarExceptions.FindCarException(notFoundCarId));

        if(driverRepository.findByCarId(carId).isPresent()) {
            throw new CarExceptions.FindCarException("Masina cu id: " + carId + " este deja asociata unui sofer!");
        }

        driver.setCar(car);
        driverRepository.save(driver);
        return driver;
    }


}
