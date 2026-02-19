package com.emergencia.prontosocorro.Config;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;

@Configuration
public class DatabaseSeeder {

   @Bean
    CommandLineRunner seedKeywordRules(
            RepositoryCID cidRepo,
            RepositoryCIDKeywordRule ruleRepo
    ) {
        return args -> {

           if (ruleRepo.count() > 1000) return;

            List<CID> cids = cidRepo.findAll();

            for (CID cid : cids) {

                if (cid.getDescription() == null) continue;

                String[] words = cid.getDescription()
                                    .toLowerCase()
                                    .split("\\s+");

                for (String word : words) {

                    if (word.length() < 5) continue;

                    if (!ruleRepo.existsByKeywordAndCid_Code(word, cid.getCode())) {

                        ruleRepo.save(
                            new CIDKeywordRule(word, cid, null)
                        );
                    }
                }
            }

            System.out.println("🔥 Keywords geradas automaticamente 😈🏥");
        };
    }

}
