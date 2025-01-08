package Sistem.de.Gestionare.a.Masinilor.jpa.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceHistoryDTO {
    private LocalDate date;
    private Integer km;
    private BigDecimal cost;
    private String details;
}
