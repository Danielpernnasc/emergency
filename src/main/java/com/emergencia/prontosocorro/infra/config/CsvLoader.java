package com.emergencia.prontosocorro.infra.config;


import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;
import com.emergencia.prontosocorro.Repository.LoaderRepository.RepositoryCID;
import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.domain.entity.CIDKeywordRule;


@Component
public class CsvLoader {


   @Bean
   CommandLineRunner seedKeywordRules(
       RepositoryCID cidRepo,
       RepositoryCIDKeywordRule ruleRepo
   ) {
     return args -> {
        if(ruleRepo.count() > 0){
            return;
        }

        List<CID> cids = cidRepo.findAll();
        List<CIDKeywordRule> batch = new ArrayList<>();

        for (CID cid: cids) {
            if(cid.getDescription() == null) continue;
            
            String[] words = cid.getDescription()
                                .toLowerCase()
                                .split("\\s+");
            for (String word : words) {

                if (word.length() < 5) continue;

                batch.add(new CIDKeywordRule(word, cid, null));

                if (batch.size() >= 500) {
                    ruleRepo.saveAll(batch);
                    batch.clear();
                }
            }

        }
     

        if(!batch.isEmpty()) {
            ruleRepo.saveAll(batch);
        }

        System.out.println("🔥 Keywords geradas!");
    };

 }

}
