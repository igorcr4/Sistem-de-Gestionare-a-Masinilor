package Sistem.de.Gestionare.a.Masinilor.jpa.repository;

import Sistem.de.Gestionare.a.Masinilor.jpa.dtos.ServiceHistoryDTO;
import Sistem.de.Gestionare.a.Masinilor.jpa.models.ServiceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long> {
    List<ServiceHistory> findByCarLicensePlate(String licensePlate);

    @Query("SELECT new Sistem.de.Gestionare.a.Masinilor.jpa.dtos.ServiceHistoryDTO(h.date, h.km, h.cost, h.details) "
           + "FROM ServiceHistory h WHERE h.date >= :date AND h.car.licensePlate = :licensePlate")
    Optional<Iterable<ServiceHistoryDTO>> findServiceHistoryFromDateToNow(@Param("date") LocalDate date, @Param("licensePlate") String licensePlate);
}
