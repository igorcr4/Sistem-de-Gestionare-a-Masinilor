package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        if (validation.isLicensePlateValidAndExists(licensePlate)) {
            throw new CarExceptions.FindCarException("Numarul de inmatriculare este invalid sau nu exista!");
        }

        Car car = carRepository.findByLicensePlate(licensePlate);

        String date = history.getDate().toString();
        validation.validateDate(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate serviceDate = LocalDate.parse(date, formatter);
        history.setDate(serviceDate);

        Integer km = history.getKm();
        validation.validateKm(km);

        String cost = history.getCost().toString();
        validation.validateCost(cost);
        BigDecimal serviceCost = new BigDecimal(cost);
        history.setCost(serviceCost);

        car.getHistory().add(history);

        carRepository.save(car);
    }

    public Iterable<ServiceHistory> getHistory(String licensePlate) {
        if (validation.isLicensePlateValidAndExists(licensePlate)) {
            throw new CarExceptions.FindCarException("Numarul de inmatriculare este invalid sau nu exista!");
        }

        List<ServiceHistory> historyList = historyRepository.findByCarLicensePlate(licensePlate);

        if (historyList.isEmpty()) {
            throw new ServiceHistoryExceptions
                    .ServiceHistoryNotFoundException("Mașina: " + licensePlate + " nu are un istoric înregistrat!");
        }

        return historyList;
    }


}
