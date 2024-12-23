package Sistem.de.Gestionare.a.Masinilor.jpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String year;
    private String licensePlate;
    private Integer currentKm;
    private Integer nextOilChangeKm;
    private LocalDate nextInsurance;

    @OneToOne
    @JoinColumn(name = "driver_id")
    @JsonBackReference
    private Driver driver;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<ServiceHistory> history = new ArrayList<>();
}


