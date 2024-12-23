package Sistem.de.Gestionare.a.Masinilor.jpa.services.impl;

import Sistem.de.Gestionare.a.Masinilor.jpa.models.Car;
import Sistem.de.Gestionare.a.Masinilor.jpa.repository.CarRepository;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.CarService;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.EmailService;
import Sistem.de.Gestionare.a.Masinilor.jpa.services.MaintenanceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MaintenanceReportImpl implements MaintenanceReport {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarService carService;
    @Autowired
    EmailService emailService;

    @Scheduled(cron = "0 0 0 */2 * *", zone = "Europe/Bucharest")
    public void checkOilChange() {
        List<Car> cars = StreamSupport.stream(carService.getAllCars().spliterator(), false).toList();

        List<Car> carsToChangeOil = cars.stream()
                                   .filter(car -> car.getCurrentKm() >= car.getNextOilChangeKm())
                                   .toList();

        if(!carsToChangeOil.isEmpty()) {
            StringBuilder emailBody = new StringBuilder("Masinile care trebuie sa faca schimb de ulei:\n");
            for(Car car : carsToChangeOil) {
                int overdueKm = car.getCurrentKm() - car.getNextOilChangeKm();
                emailBody.append("Numarul de inmatriculare: ").append(car.getLicensePlate())
                        .append(", a depasit termenul limita cu : ").append(overdueKm).append(" km")
                        .append("\n");
            }
            emailService.mailSender("igor.caravai2345@gmail.com", "Ulei", emailBody);
            carsToChangeOil.forEach(car -> {
                car.setNextOilChangeKm(car.getCurrentKm() + 9500);
                carRepository.save(car);
            });
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkInsuranceDate() {
        LocalDate today = LocalDate.now();
        LocalDate twoDaysBefore = today.plusDays(2);

        List<Car> cars = carRepository.findCarsWithInsuranceExpiring(twoDaysBefore);

        if(!cars.isEmpty()) {
            StringBuilder emailBody = new StringBuilder("Mașinile care necesită actualizarea asigurării in 2 zile:\n");

            for(Car car : cars) {
                emailBody.append("Numarul de inmatriculare: ").append(car.getLicensePlate()).append("\n");
            }

            emailService.mailSender("igor.caravai2345@gmail.com", "Asigurare", emailBody);
            cars.forEach(car -> {
                car.setNextInsurance(today.plusYears(1));
                carRepository.save(car);
            });
        }
    }

    @Scheduled(cron = "0 0 0 25 10 *")
    public void sendTireChangeAlert() {
        emailService.mailSender("igor.caravai2345@gmail.com", "Asigurare", new StringBuilder("Este timpul să schimbi anvelopele vehiculului tău." +
                " Te rugăm să programezi schimbarea cât mai curând posibil."));
    }
}
