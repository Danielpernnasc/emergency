package com.emergencia.prontosocorro.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

@Profile("!test")
@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner seedKeywordRules(
            RepositoryCID cidRepo,
            RepositoryCIDKeywordRule ruleRepo
    ) {
        return args -> generateKeywords(cidRepo, ruleRepo);
    }

    private void generateKeywords(
            RepositoryCID cidRepo,
            RepositoryCIDKeywordRule ruleRepo
    ) {

        if (ruleRepo.count() > 1000) {
            System.out.println("⚠️ Keywords já geradas. Pulando...");
            return;
        }

        List<CID> cids = cidRepo.findAll();

        for (CID cid : cids) {
            processCid(cid, ruleRepo);
        }

        System.out.println("🔥 Keywords geradas automaticamente 😈🏥");
    }

    private void processCid(CID cid, RepositoryCIDKeywordRule ruleRepo) {

        if (cid.getDescription() == null) return;

        String[] words = cid.getDescription()
                .toLowerCase()
                .split("\\s+");

        for (String word : words) {

            if (word.length() < 5) continue;

            if (!ruleRepo.existsByKeywordAndCid_Code(word, cid.getCode())) {
                ruleRepo.save(new CIDKeywordRule(word, cid, null));
            }
        }
    }
}
