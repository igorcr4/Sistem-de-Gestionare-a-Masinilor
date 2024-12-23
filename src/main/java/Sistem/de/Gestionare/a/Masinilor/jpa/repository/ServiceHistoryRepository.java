package Sistem.de.Gestionare.a.Masinilor.jpa.repository;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {
    List<ServiceHistory> findByCarLicensePlate(String licensePlate);
}
