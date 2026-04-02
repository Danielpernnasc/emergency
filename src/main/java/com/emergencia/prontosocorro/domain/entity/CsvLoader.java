package com.emergencia.prontosocorro.domain.entity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

@Profile("!test")
@Component
public class CsvLoader implements ApplicationRunner {
     
    private static final Logger log = LoggerFactory.getLogger(CsvLoader.class);

    private final RepositoryCID repository;

    public CsvLoader(RepositoryCID repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        load();
    }

    protected BufferedReader createReader(String path) throws Exception {
    InputStream is = getResource(path);

        return new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
    }

     public void load() throws Exception {

        if (repository.count() > 0) {
            log.warn("⚠️ CIDs já carregados. Pulando...");
            return;
        }

        List<CID> batch = new ArrayList<>();

        for (String path : getCsvPaths()) {
            processFile(path, batch);
        }

        if (!batch.isEmpty()) {
            repository.saveAll(batch);
        }

        log.info("🔥 CIDs carregados com sucesso!");
    }

    private void processFile(String path, List<CID> batch) throws Exception {

        try (BufferedReader reader = createReader(path)){

            String line;
            reader.readLine(); // pula header

            while ((line = reader.readLine()) != null) {
                parseAndAdd(line, batch);
            }
        }
    }

    public void parseAndAdd_(String line, List<CID> batch) {
        parseAndAdd(line, batch);
        
    }

    private void parseAndAdd(String line, List<CID> batch) {

        String[] data = line.split(";", -1);

        if (data.length < 3) return;

        String code = data[0].trim();
        String desc = data[2].trim();

        if (code.isBlank() || desc.isBlank()) return;

        CID cid = new CID();
        cid.setCode(code);
        cid.setDescription(desc);

        batch.add(cid);

        if (batch.size() >= 500) {
            repository.saveAll(batch);
            batch.clear();
        }
    }


    private InputStream getResource(String path) {

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path.replaceFirst("^/", ""));

        if (is == null) {
            throw new RuntimeException("❌ Arquivo não encontrado: " + path);
        }

        return is;
    }

    private List<String> getCsvPaths() {
        return List.of(
                "CID10CSV/CID-10-CATEGORIAS.CSV",
                "CID10CSV/CID-10-SUBCATEGORIAS.CSV"
        );
    }
}