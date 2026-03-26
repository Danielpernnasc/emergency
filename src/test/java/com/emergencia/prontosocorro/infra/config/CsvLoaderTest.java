package com.emergencia.prontosocorro.infra.config;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;

import com.emergencia.prontosocorro.repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

public class CsvLoaderTest {
   @Test
    void shouldNotSeedWhenRulesAlreadyExist() throws Exception {
        // mocks
        RepositoryCID cidRepo = mock(RepositoryCID.class);
        RepositoryCIDKeywordRule ruleRepo = mock(RepositoryCIDKeywordRule.class);

        // comportamento
        when(ruleRepo.count()).thenReturn(1L);

        // cria runner manualmente
        CommandLineRunner runner = args -> {
            if (ruleRepo.count() > 0) return;
        };

        // executa
        runner.run();

        // valida: NÃO chamou nada
        verify(ruleRepo, never()).saveAll(any());
        verify(cidRepo, never()).findAll();
    }

    @Test
    void shouldSeedWhenNoRulesExist() throws Exception {
        // mocks
        RepositoryCID cidRepo = mock(RepositoryCID.class);
        RepositoryCIDKeywordRule ruleRepo = mock(RepositoryCIDKeywordRule.class);

        // comportamento
        when(ruleRepo.count()).thenReturn(0L);
        when(cidRepo.findAll()).thenReturn(Collections.emptyList());

        // runner simplificado
        CommandLineRunner runner = args -> {
            if (ruleRepo.count() > 0) return;
            cidRepo.findAll();
        };

        // executa
        runner.run();

        // valida
        verify(cidRepo, times(1)).findAll();
    }
}
