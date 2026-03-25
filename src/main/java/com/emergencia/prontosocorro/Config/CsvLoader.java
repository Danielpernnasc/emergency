package com.emergencia.prontosocorro.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

import jakarta.annotation.PostConstruct;

@Component
public class CsvLoader {
    @Autowired
    private RepositoryCID repository;

    @PostConstruct
    public void load() throws Exception {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                getClass().getResourceAsStream("/CID10CSV/CID-10-CATEGORIAS.CSV")
            )
        );

          List<CID> batch = new ArrayList<>();

        String line;
        reader.readLine(); // pula header

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");

             // validação
            if (data.length < 3) continue;
            if (data[1].isBlank()) continue;

             CID cid = new CID();
            cid.setCode(data[0].trim());
            cid.setDescription(data[2].trim());
            
            batch.add(cid);

            if (batch.size() >= 500) {
                repository.saveAll(batch);
                batch.clear();
            }
        }
          // salva o resto
        if (!batch.isEmpty()) {
            repository.saveAll(batch);
        }

        System.out.println("🔥 CIDs carregados com sucesso!");
    }

}
