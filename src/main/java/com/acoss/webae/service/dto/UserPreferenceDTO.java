package com.acoss.webae.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.PreferenceAvatar;
import com.acoss.webae.domain.enumeration.PreferenceAbonnement;

/**
 * A DTO for the UserPreference entity.
 */
public class UserPreferenceDTO implements Serializable {

    private Long id;

    private String numCompteExterne;

    private PreferenceAvatar avatarPref;

    private PreferenceAbonnement abonnePref;

    private Set<PreferenceNotifDTO> preferenceNotifs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCompteExterne() {
        return numCompteExterne;
    }

    public void setNumCompteExterne(String numCompteExterne) {
        this.numCompteExterne = numCompteExterne;
    }

    public PreferenceAvatar getAvatarPref() {
        return avatarPref;
    }

    public void setAvatarPref(PreferenceAvatar avatarPref) {
        this.avatarPref = avatarPref;
    }

    public PreferenceAbonnement getAbonnePref() {
        return abonnePref;
    }

    public void setAbonnePref(PreferenceAbonnement abonnePref) {
        this.abonnePref = abonnePref;
    }

    public Set<PreferenceNotifDTO> getPreferenceNotifs() {
        return preferenceNotifs;
    }

    public void setPreferenceNotifs(Set<PreferenceNotifDTO> preferenceNotifs) {
        this.preferenceNotifs = preferenceNotifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPreferenceDTO userPreferenceDTO = (UserPreferenceDTO) o;
        if(userPreferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPreferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPreferenceDTO{" +
            "id=" + getId() +
            ", numCompteExterne='" + getNumCompteExterne() + "'" +
            ", avatarPref='" + getAvatarPref() + "'" +
            ", abonnePref='" + getAbonnePref() + "'" +
            "}";
    }
}
