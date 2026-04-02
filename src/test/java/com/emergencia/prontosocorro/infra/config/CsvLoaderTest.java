package com.emergencia.prontosocorro.infra.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.domain.entity.CsvLoader;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

class CsvLoaderTest {

    @Test
    void shouldLoadDataFromCsv() throws Exception {

        // mock do repository
        RepositoryCID repository = mock(RepositoryCID.class);

        when(repository.count()).thenReturn(0L);

        // CSV fake (simula arquivo real)
      

        // cria loader sobrescrevendo leitura
        CsvLoader loader = new CsvLoader(repository) {
            
            protected List<String> getCsvPaths() {
                return List.of("fake");
            }

            @Override
            protected BufferedReader createReader(String path) {
                return new BufferedReader(new StringReader(
                    "CAT;CLASSIF;DESCRICAO\nA90;;Dengue\n"
                ));
            }
        };

        loader.load();

        // verifica se salvou
        verify(repository, atLeastOnce()).saveAll(any());
    }

    @Test
    void shouldIgnoreInvalidLines() {

        RepositoryCID repository = mock(RepositoryCID.class);
        CsvLoader loader = new CsvLoader(repository);

        List<CID> batch = new ArrayList<>();

        loader.parseAndAdd_(";;", batch);

        assertTrue(batch.isEmpty());
    }

   @Test
    void shouldThrowWhenFileNotFound() {

    RepositoryCID repository = mock(RepositoryCID.class);
    when(repository.count()).thenReturn(0L);

    CsvLoader loader = new CsvLoader(repository) {

            protected List<String> getCsvPaths() {
                return List.of("fake");
            }
           
            @Override
            protected BufferedReader createReader(String path) {
                throw new RuntimeException("Erro forçado");
            }
        };

        assertThrows(RuntimeException.class, loader::load);
    }
}