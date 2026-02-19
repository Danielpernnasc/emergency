package com.emergencia.prontosocorro.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emergencia.prontosocorro.Domain.Entity.CIDKeywordRule;

public interface RepositoryCIDKeywordRule  extends JpaRepository<CIDKeywordRule, Long> {
     @Query("""
        SELECT r FROM CIDKeywordRule r
        WHERE LOWER(:desc) LIKE LOWER(CONCAT('%', r.keyword, '%'))
        ORDER BY LENGTH(r.keyword) DESC
    """)
    Optional<CIDKeywordRule> matchKeyword(@Param("desc") String description);

    boolean existsByKeywordAndCid_Code(String keyword, String cidCode);


}
