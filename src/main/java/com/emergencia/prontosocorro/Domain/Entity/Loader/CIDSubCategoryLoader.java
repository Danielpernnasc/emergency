package com.emergencia.prontosocorro.Domain.Entity.Loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Domain.Entity.CIDSubCategory;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCIDSubCategory;

@Component
public class CIDSubCategoryLoader implements CommandLineRunner {
 
    private final RepositoryCIDSubCategory repositoryCIDSubCategory;

    public CIDSubCategoryLoader(RepositoryCIDSubCategory repositoryCIDSubCategory) {
        this.repositoryCIDSubCategory = repositoryCIDSubCategory;
    }

    @Override
    public void run(String... args) throws Exception {
        if(repositoryCIDSubCategory.count() > 0) return;

        ClassPathResource resource =
                new ClassPathResource("CID10CSV/CID-10-SUBCATEGORIAS.CSV");

                try(BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.ISO_8859_1 ))) {
                    reader.lines()
                    .skip(1)
                    .map(line -> line.split(";"))
                    .forEach(data -> {
                         if (data.length < 5) return;
                         
                        CIDSubCategory subCategory = new CIDSubCategory(
                            data[0].trim(),
                            data[4].trim()
                        );

                        repositoryCIDSubCategory.save(subCategory);
                    });
            
                System.out.println("✅ Subgrupos carregados");
            }
           
    }

}
