package Sistem.de.Gestionare.a.Masinilor.jpa.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DriverDTO{
    private Long id;
    private String firstName;
    private String lastName;
    private String licensePlate;
}
