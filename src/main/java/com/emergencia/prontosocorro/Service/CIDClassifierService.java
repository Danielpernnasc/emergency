package com.emergencia.prontosocorro.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;
import com.emergencia.prontosocorro.Repository.RepositoryCIDKeywordRule;


@Service
public class CIDClassifierService {


   
        private final RepositoryCIDKeywordRule repositoryCIDKeywordRule;

        public CIDClassifierService(RepositoryCIDKeywordRule repositoryCIDKeywordRule) {
         
            this.repositoryCIDKeywordRule = repositoryCIDKeywordRule;
        }

        public CID classify(String description) {

            if (description == null || description.isBlank())
                return null;

            String desc = description.toLowerCase();

            List<CIDKeywordRule> rules = repositoryCIDKeywordRule.findAll();

            for (CIDKeywordRule rule : rules) {

                if (desc.contains(rule.getKeyword().toLowerCase())) {

                    return rule.getCid();   // 💥 MATCH DIRETO 😈🔥
                }
            }

            return null;
        }


}
