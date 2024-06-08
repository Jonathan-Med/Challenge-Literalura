package com.aluracursos.challengeliteralura;

import com.aluracursos.challengeliteralura.principal.Principal;
import com.aluracursos.challengeliteralura.repository.AutorRepository;
import com.aluracursos.challengeliteralura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplicationConsole implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraluraApplicationConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroRepository, autorRepository);
        principal.mostrarMenu();
    }
}