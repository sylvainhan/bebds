package com.acoss.webae.repository;

import com.acoss.webae.domain.UserDevice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the UserDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    List<UserDevice> findAllByUserPreferenceId(Long id);

    List<UserDevice> findAllByDeviceToken(String deviceToken);
}
