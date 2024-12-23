package Sistem.de.Gestionare.a.Masinilor.jpa.controllers;

import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.ServiceHistoryExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.ServiceHistoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-history")
public class ServiceHistoryController {
    @Autowired
    ServiceHistoryInterface serviceHistoryInterface;

    @PostMapping("/{licensePlate}")
    public ResponseEntity<String> addServiceHistory(@PathVariable String licensePlate, @RequestBody ServiceHistory serviceHistory) {
        serviceHistoryInterface.addNewServiceHistory(licensePlate, serviceHistory);
        return ResponseEntity.ok("Istoricul de service a fost adăugat cu succes pentru mașina: " + licensePlate);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<Iterable<ServiceHistory>> getHistory(@PathVariable String licensePlate) {
        Iterable<ServiceHistory> serviceHistory =  serviceHistoryInterface.getHistory(licensePlate);
        return ResponseEntity.ok(serviceHistory);
    }







    @ExceptionHandler(ServiceHistoryExceptions.ServiceHistoryNotFoundException.class)
    public ResponseEntity<String> notFound(ServiceHistoryExceptions.ServiceHistoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarExceptions.FindCarException.class)
    public ResponseEntity<String> findCars(CarExceptions.FindCarException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CarExceptions.InformationUpdateException.class)
    public ResponseEntity<String> updateInformation(CarExceptions.InformationUpdateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
