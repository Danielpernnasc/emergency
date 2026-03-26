package com.emergencia.prontosocorro.infra.config;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.repository.loaderRepository.RepositoryCID;

public class DatabaseSeederTest {
@Test
    void shouldNotGenerateWhenRulesAlreadyExist() {

        RepositoryCID cidRepo = mock(RepositoryCID.class);
        RepositoryCIDKeywordRule ruleRepo = mock(RepositoryCIDKeywordRule.class);
    

        when(ruleRepo.count()).thenReturn(2000L);

        DatabaseSeeder seeder = new DatabaseSeeder();

        seeder.generateKeywords_(cidRepo, ruleRepo);

        verify(cidRepo, never()).findAll();
    }

    @Test
        void shouldGenerateKeywords() {

            RepositoryCID cidRepo = mock(RepositoryCID.class);
            RepositoryCIDKeywordRule ruleRepo = mock(RepositoryCIDKeywordRule.class);

            when(ruleRepo.count()).thenReturn(0L);

            CID cid = new CID();
            cid.setCode("A90");
            cid.setDescription("Dengue hemorrágica grave");

            when(cidRepo.findAll()).thenReturn(List.of(cid));
            when(ruleRepo.existsByKeywordAndCid_Code(any(), any())).thenReturn(false);

            DatabaseSeeder seeder = new DatabaseSeeder();

            seeder.generateKeywords_(cidRepo, ruleRepo);

            verify(ruleRepo, atLeastOnce()).save(any());
        }


        @Test
        void shouldIgnoreShortWords() {

            RepositoryCIDKeywordRule ruleRepo = mock(RepositoryCIDKeywordRule.class);

            CID cid = new CID();
            cid.setCode("A90");
            cid.setDescription("dor no pé");

            DatabaseSeeder seeder = new DatabaseSeeder();

            seeder.processCID_(cid, ruleRepo);

            verify(ruleRepo, never()).save(any());
        }
}
