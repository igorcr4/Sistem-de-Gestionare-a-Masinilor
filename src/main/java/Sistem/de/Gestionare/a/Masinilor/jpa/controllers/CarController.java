package Sistem.de.Gestionare.a.Masinilor.jpa.controllers;


import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.CarDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    CarService service;

    @PostMapping("/create")
    public ResponseEntity<Car> createOneCar(@RequestBody Car car) {
        Car createOne = service.saveCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOne);
    }

    @PostMapping("/createMultiple")
    public ResponseEntity<Iterable<Car>> createMultipleCar(@RequestBody List<Car> cars){
        Iterable<Car> createMultiple = service.saveAll(cars);
        return ResponseEntity.status(HttpStatus.CREATED).body(createMultiple);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Car>> getAllCars() {
        Iterable<Car> getAll = service.getAllCars();
        return ResponseEntity.ok(getAll);
    }

    @GetMapping("/licensePlate")
    public ResponseEntity<CarDTO> findByLicensePlate(@RequestParam String licensePlate) {
        CarDTO getCarWithDriver = service.findByLicensePlate(licensePlate);
        return ResponseEntity.ok(getCarWithDriver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) {
        Car byId = service.findById(id);
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/licensePlate")
    public ResponseEntity<Car> delete(@RequestParam String licensePlate) {
        service.deleteByLicensePlate(licensePlate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateKm/{carId}")
    public ResponseEntity<String> updateKm(@PathVariable Long carId, @RequestParam Integer newKm) {
        service.updateKm(carId, newKm);
        return ResponseEntity.ok("Kilometrajul masinii a fost actualizat cu succes!");
    }

    @PatchMapping("/setOilChange/{carId}")
    public ResponseEntity<String> setOilChangeKm(@PathVariable Long carId, @RequestParam Integer oilChange) {
        service.setOilChange(carId, oilChange);
        return ResponseEntity.ok("Uleiul va trebui schimbat la: " + oilChange + " km");
    }

    @PatchMapping("/setInsurance/{carId}")
    public ResponseEntity<String> setInsurance(@PathVariable Long carId, @RequestParam LocalDate date) {
        service.setInsurance(carId, date);
        return ResponseEntity.ok("Asigurarea va trebui refacuta la data de: " + date);
    }









    @ExceptionHandler(CarExceptions.CarCreateValidationException.class)
    public ResponseEntity<String> validationCars(CarExceptions.CarCreateValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CarExceptions.FindCarException.class)
    public ResponseEntity<String> findCars(CarExceptions.FindCarException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarExceptions.InformationUpdateException.class)
    public ResponseEntity<String> updateInformation(CarExceptions.InformationUpdateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CarExceptions.InformationValidityException.class)
    public ResponseEntity<String> validationCars(CarExceptions.InformationValidityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}








