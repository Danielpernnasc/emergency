package com.emergencia.prontosocorro.Domain.Entity.Loader.CID10;

import org.springframework.boot.CommandLineRunner;

import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDGroup;
import com.emergencia.prontosocorro.Repository.LoaderRepository.CID10.RepositoryCIDGroup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class CIDGroupLoader implements CommandLineRunner {

    private final RepositoryCIDGroup repositoryCidGroup;

    public CIDGroupLoader(RepositoryCIDGroup repositoryCidGroup) {
        this.repositoryCidGroup = repositoryCidGroup;
    }

    @Override
    public void run(String... args) throws Exception {
        if(repositoryCidGroup.count() > 0) return;


        ClassPathResource resource = new ClassPathResource("CID10CSV/CID-10-GRUPOS.CSV");

    try(BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            resource.getInputStream(),
            StandardCharsets.ISO_8859_1))) {

            reader.lines()
                  .skip(1)
                  .map(line -> line.split(";"))
                  .forEach(data -> {
                     if (data.length < 4) return;
                    CIDGroup group = new CIDGroup(
                        data[1].trim(),
                        data[3].trim()
                    );

                    repositoryCidGroup.save(group);
                  });
            System.out.println("✅ Grupos carregados");
            }
    
        
        // Implementação para carregar os grupos CID
    }

}
