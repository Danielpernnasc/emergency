package com.emergencia.prontosocorro.Domain.Entity.Loader;

import org.springframework.boot.CommandLineRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Domain.Entity.Capitulos;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCapitulos;

@Component
public class CapituloLoader implements CommandLineRunner {

    private final RepositoryCapitulos repositoryCapitulos;
    
    public CapituloLoader(RepositoryCapitulos repositoryCapitulos) {
        this.repositoryCapitulos = repositoryCapitulos;
    }

    @Override
    public void run(String... args) throws Exception {
        if(repositoryCapitulos.count() > 0) return;

         try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new ClassPathResource("CID10CSV/CID-10-CAPITULOS.CSV").getInputStream(),
                StandardCharsets.ISO_8859_1))) {
            reader.lines()
                .skip(1)
                .map(line -> line.split(";"))
                .forEach(data -> {
                    if (data.length < 3) return;

                    Capitulos capitulo = new Capitulos(
                        data[0].trim(),
                        data[3].trim()
                    );

                    repositoryCapitulos.save(capitulo);
                });

       }

    }
}
