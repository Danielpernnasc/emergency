package com.emergencia.prontosocorro.infra.config;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

class CsvLoaderTest {

    @Test
    void shouldLoadDataFromCsv() throws Exception {

        // mock do repository
        RepositoryCID repository = mock(RepositoryCID.class);

        when(repository.count()).thenReturn(0L);

        // CSV fake (simula arquivo real)
        String fakeCsv =
                "CAT;CLASSIF;DESCRICAO\n" +
                "A90;;Dengue\n" +
                "B01;;Catapora\n";

        BufferedReader fakeReader = new BufferedReader(new StringReader(fakeCsv));

        // cria loader sobrescrevendo leitura
        CsvLoader loader = new CsvLoader(repository) {
            
            protected List<String> getCsvPaths() {
                return List.of("fake");
            }

            @Override
            protected BufferedReader createReader(String path) {
                return fakeReader;
            }
        };

        loader.load();

        // verifica se salvou
        verify(repository, atLeastOnce()).saveAll(any());
    }
}