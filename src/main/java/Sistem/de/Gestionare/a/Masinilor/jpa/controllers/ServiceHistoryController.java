package Sistem.de.Gestionare.a.Masinilor.jpa.controllers;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.ServiceHistoryDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.ServiceHistoryExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.ServiceHistoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateServiceInfo(@PathVariable Long id, @RequestBody ServiceHistoryDTO dto) {
        serviceHistoryInterface.updateInformation(id, dto);
        return ResponseEntity.ok("Istoricul de service a fost actualizat cu succes!");
    }

    @GetMapping("/from")
    public ResponseEntity<Iterable<ServiceHistoryDTO>> historyFrom(@RequestParam LocalDate date, @RequestParam String licensePlate) {
        Iterable<ServiceHistoryDTO> history = serviceHistoryInterface.findServiceHistorySinceDate(date, licensePlate);
        return ResponseEntity.ok(history);
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
