package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.ServiceHistoryDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.CarExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.exceptions.ServiceHistoryExceptions;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.ServiceHistoryRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.ServiceHistoryInterface;
import Sistem.de.Gestionare.a.Masinilor.jpa.validation.CarValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceHistoryImpl implements ServiceHistoryInterface {
    @Autowired
    ServiceHistoryRepository historyRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarValidation validation;


    public void addNewServiceHistory(String licensePlate, ServiceHistory history) {
        validation.validateLicensePlate(licensePlate);

        Car car = carRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new CarExceptions.FindCarException("Masina cu numarul de inmatriculare: " + licensePlate + " nu exista!"));

        validation.validatePastDate(history.getDate());

        validation.validateKm(history.getKm());

        validation.validateCost(history.getCost());

        history.setCar(car);
        car.getHistory().add(history);

        carRepository.save(car);
    }

    public Iterable<ServiceHistory> getHistory(String licensePlate) {
        validation.validateLicensePlate(licensePlate);

        List<ServiceHistory> historyList = historyRepository.findByCarLicensePlate(licensePlate);

        if (historyList.isEmpty()) {
            throw new ServiceHistoryExceptions
                    .ServiceHistoryNotFoundException("Mașina: " + licensePlate + " nu are un istoric înregistrat!");
        }

        return historyList;
    }

    public void updateInformation(Long id, ServiceHistoryDTO dto) {
        validation.validateId(id);

        ServiceHistory history = historyRepository.findById(id)
                       .orElseThrow(() -> new CarExceptions.FindCarException("Masina cu id: " + id + " nu exista!"));

        if(dto.getDate() != null) {
        validation.validatePastDate(dto.getDate());
        history.setDate(dto.getDate());
        }

        if(dto.getKm() != null) {
            validation.validateKm(dto.getKm());
            history.setKm(dto.getKm());
        }

        if(dto.getCost() != null) {
            validation.validateCost(dto.getCost());
            history.setCost(dto.getCost());
        }

        if(dto.getDetails() != null) {
            history.setDetails(dto.getDetails());
        }

        historyRepository.save(history);
    }

    public Iterable<ServiceHistoryDTO> findServiceHistorySinceDate(LocalDate date, String licensePlate) {
        validation.validatePastDate(date);
        validation.validateLicensePlate(licensePlate);

        return historyRepository.findServiceHistoryFromDateToNow(date, licensePlate)
                .orElseThrow(() -> new CarExceptions.FindCarException("Nu exista istoric inregistrat de la data: " + date + " pana astazi!"));
    }


}
