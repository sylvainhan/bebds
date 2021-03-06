package com.acoss.webae.repository;

import com.acoss.webae.domain.Demande;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Demande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {

}
