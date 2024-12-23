package Sistem.de.Gestionare.a.Masinilor.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemDeGestionareAMasinilorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemDeGestionareAMasinilorApplication.class, args);
	}

}
