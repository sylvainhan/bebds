package com.acoss.webae.repository;

import com.acoss.webae.domain.PreferenceNotif;
import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PreferenceNotif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferenceNotifRepository extends JpaRepository<PreferenceNotif, Long> {

    void deleteByUserPreferenceIdAndChannelAndMoment(Long userPreferenceId, NotifChannel channel, NotifMoment type);
}
