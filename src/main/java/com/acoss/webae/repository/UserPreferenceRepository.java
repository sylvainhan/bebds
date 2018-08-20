package com.acoss.webae.repository;

import com.acoss.webae.domain.UserPreference;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    UserPreference findOneByNumCompteExterne(String num_cpt_ext);
}
