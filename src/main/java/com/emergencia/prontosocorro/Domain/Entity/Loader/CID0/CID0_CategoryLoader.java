package com.emergencia.prontosocorro.Domain.Entity.Loader.CID0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

import com.emergencia.prontosocorro.Domain.Entity.CID0.CIDCategory0;
import com.emergencia.prontosocorro.Repository.LoaderRepository.CID0.RepositoryCID0_Category;

import org.springframework.stereotype.Component;

@Component
public class CID0_CategoryLoader implements CommandLineRunner {
    private final RepositoryCID0_Category repositoryCID0Category;

    public CID0_CategoryLoader(RepositoryCID0_Category repositoryCID0Category) {
        this.repositoryCID0Category = repositoryCID0Category;
    }

    @Override
    public void run(String... args) throws IOException {
        System.out.println("🔥 Loader CID-0 executando");

        if(repositoryCID0Category.count() > 0) return;

        ClassPathResource resource = new ClassPathResource("CID10CSV/CID-O-CATEGORIAS.CSV");

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(resource.getInputStream(),
                  StandardCharsets.ISO_8859_1))){

                reader.lines()
                  .skip(1)
                  .map(line -> line.split(";"))
                  .forEach(data -> {
                    if (data.length < 2) return;
                    CIDCategory0 category = new CIDCategory0(
                        data[0],   // code
                        data[1]    // description
                    );

                    repositoryCID0Category.save(category);
                  });
            }
    }

}
