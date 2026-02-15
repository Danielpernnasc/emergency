package com.emergencia.prontosocorro.Domain.Entity.Loader.CID10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDCategory;
import com.emergencia.prontosocorro.Repository.LoaderRepository.CID10.RepositoryCIDCategory;

@Component
public class CIDCategoryLoader implements CommandLineRunner {

    private final RepositoryCIDCategory repository;

    public CIDCategoryLoader(RepositoryCIDCategory repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repository.count() > 0) return;

        ClassPathResource resource =
                new ClassPathResource("CID10CSV/CID-10-CATEGORIAS.CSV");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.ISO_8859_1))) {

            reader.lines()
                    .skip(1)
                    .map(line -> line.split(";"))
                    .forEach(data -> {
                        if (data.length < 3) return;
                        CIDCategory category = new CIDCategory(
                                data[0].trim(),
                                data[2].trim()
                        );

                        repository.save(category);
                    });

            System.out.println("✅ Categorias carregadas");
        }
    }
}

