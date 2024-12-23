package Sistem.de.Gestionare.a.Masinilor.jpa.services;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;

public interface ServiceHistoryInterface {
    void addNewServiceHistory(String licensePlate, ServiceHistory history);
    Iterable<ServiceHistory> getHistory(String licensePlate);
}
