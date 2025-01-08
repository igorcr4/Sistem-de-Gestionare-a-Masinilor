package Sistem.de.Gestionare.a.Masinilor.jpa.repository;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByLicensePlate(String licensePlate);

    @Query("SELECT l FROM Car l WHERE l.licensePlate = :licensePlate")
    Optional<Car> findByLicensePlate(@Param("licensePlate") String licensePlate);

    @Query("SELECT c FROM Car c WHERE c.nextInsurance = :twoDaysLater")
    List<Car> findCarsWithInsuranceExpiring(@Param("twoDaysLater")LocalDate twoDaysLater);

}

