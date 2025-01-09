package Sistem.de.Gestionare.a.Masinilor.jpa;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.CarService;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.EmailService;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.impl.MaintenanceReportImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintenanceReportsTests {
    @InjectMocks
    MaintenanceReportImpl maintenanceReport;
    @Mock
    CarService carService;
    @Mock
    EmailService emailService;
    @Mock
    CarRepository carRepository;

    @Test
    public void checkOilChangeTest() {
        Car car1 = new Car();
        Car car2 = new Car();

        car1.setLicensePlate("B 02 ABC");
        car2.setLicensePlate("B 02 DDD");

        car1.setCurrentKm(10000);
        car2.setCurrentKm(5000);

        car1.setNextOilChangeKm(9500);
        car2.setNextOilChangeKm(6000);

        List<Car> cars = List.of(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        doNothing().when(emailService).mailSender(anyString(), anyString(), any(StringBuilder.class));

        maintenanceReport.checkOilChange();

        ArgumentCaptor<StringBuilder> emailCaptor = ArgumentCaptor.forClass(StringBuilder.class);
        verify(emailService, times(1))
                             .mailSender(eq("igor.caravai2345@gmail.com"), eq("Ulei"), emailCaptor.capture());

        String emailBody = emailCaptor.getValue().toString();;
        assertTrue(emailBody.contains("B 02 ABC"));
        assertTrue(emailBody.contains(", a depasit termenul limita cu : 500 km"));
    }
}
