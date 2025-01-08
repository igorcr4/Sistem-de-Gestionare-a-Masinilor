package Sistem.de.Gestionare.a.Masinilor.jpa.controllers;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.DriverDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.DriverExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Driver;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService service;

    @PostMapping("/create")
    public ResponseEntity<Driver> createOneDriver(@RequestBody Driver driver) {
        Driver createOne = service.saveDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOne);
    }

    @PostMapping("/createMultiple")
    public ResponseEntity<Iterable<Driver>> createMultipleDrivers(@RequestBody List<Driver> drivers){
        Iterable<Driver> createMultiple = service.saveAll(drivers);
        return ResponseEntity.status(HttpStatus.CREATED).body(createMultiple);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Driver>> getAllDrivers(){
        Iterable<Driver> getAll = service.getAllDrivers();
        return ResponseEntity.ok(getAll);
    }

    @GetMapping
    public ResponseEntity<List<DriverDTO>> getByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        List<DriverDTO> getDriverWithCar = service.findByFullName(firstName, lastName);
        return ResponseEntity.ok(getDriverWithCar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> driverById(@PathVariable Long id) {
        Driver byId = service.findById(id);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id){
        service.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{driverId}/{carId}")
    public ResponseEntity<Driver> map(@PathVariable Long driverId, @PathVariable Long carId) {
        Driver driver = service.mapDriverToCar(driverId, carId);
        return ResponseEntity.ok(driver);
    }







    //prinde toate exceptiile care pot aparea la crearea unui sau mai multi soferi
    @ExceptionHandler(DriverExceptions.ValidationException.class)
    public ResponseEntity<String> validationDriversException(DriverExceptions.ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //prinde toate exceptiile care pot aparea la cautarea unui sau mai multi soferi
    @ExceptionHandler(DriverExceptions.FindException.class)
    public ResponseEntity<String> findDriversException(DriverExceptions.FindException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    //prinde exceptia cu maparea unui sofer la o masina
    @ExceptionHandler(CarExceptions.FindCarException.class)
    public ResponseEntity<String> mapException(CarExceptions.FindCarException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }



}
