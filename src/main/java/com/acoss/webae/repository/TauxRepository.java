package com.acoss.webae.repository;

import com.acoss.webae.domain.Taux;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Taux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TauxRepository extends JpaRepository<Taux, Long> {

}
