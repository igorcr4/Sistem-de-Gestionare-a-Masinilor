package Sistem.de.Gestionare.a.Masinilor.jpa.services;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.ServiceHistoryDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;

import java.time.LocalDate;

public interface ServiceHistoryInterface {
    void addNewServiceHistory(String licensePlate, ServiceHistory history);

    Iterable<ServiceHistory> getHistory(String licensePlate);

    void updateInformation(Long id, ServiceHistoryDTO dto);

    Iterable<ServiceHistoryDTO> findServiceHistorySinceDate(LocalDate date, String licensePlate);
}
