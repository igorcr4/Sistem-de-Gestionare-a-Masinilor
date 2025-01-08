package Sistem.de.Gestionare.a.Masinilor.jpa.repository;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{
    boolean existsByCnp(String cnp);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<Driver> findByCarId(Long carId);


    @Query("SELECT d FROM Driver d WHERE d.firstName = :firstName AND d.lastName = :lastName")
    List<Driver> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}


