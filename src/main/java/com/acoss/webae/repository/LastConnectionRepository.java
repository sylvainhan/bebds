package com.acoss.webae.repository;

import com.acoss.webae.domain.LastConnection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LastConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LastConnectionRepository extends JpaRepository<LastConnection, Long> {

}
