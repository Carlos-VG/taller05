package co.edu.unicauca.asae.taller05;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.unicauca.asae.taller05.service.Taller05UseCases;

@SpringBootApplication
public class Taller05Application implements CommandLineRunner {

	private final Taller05UseCases svc;

	public Taller05Application(Taller05UseCases svc) {
		this.svc = svc;
	}

	public static void main(String[] args) {
		SpringApplication.run(Taller05Application.class, args);
	}

	@Override
	public void run(String... args) {

	}
}
