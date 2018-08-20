package com.acoss.webae.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.TypeDeNotif;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the Notification entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    private Boolean estLu;

    private TypeDeNotif type;

    private String customSujet;

    private String customContenu;

    private String customUrl;

    private Long userPreferenceId;

    private String userPreferenceNumCompteExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstLu() {
        return estLu;
    }

    public void setEstLu(Boolean estLu) {
        this.estLu = estLu;
    }

    public TypeDeNotif getType() {
        return type;
    }

    public void setType(TypeDeNotif type) {
        this.type = type;
    }

    public String getCustomSujet() {
        return customSujet;
    }

    public void setCustomSujet(String customSujet) {
        this.customSujet = customSujet;
    }

    public String getCustomContenu() {
        return customContenu;
    }

    public void setCustomContenu(String customContenu) {
        this.customContenu = customContenu;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    @JsonIgnore
    public Long getUserPreferenceId() {
        return userPreferenceId;
    }

    @JsonProperty
    public void setUserPreferenceId(Long userPreferenceId) {
        this.userPreferenceId = userPreferenceId;
    }

    public String getUserPreferenceNumCompteExterne() {
        return userPreferenceNumCompteExterne;
    }

    public void setUserPreferenceNumCompteExterne(String userPreferenceNumCompteExterne) {
        this.userPreferenceNumCompteExterne = userPreferenceNumCompteExterne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if(notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", estLu='" + isEstLu() + "'" +
            ", type='" + getType() + "'" +
            ", customSujet='" + getCustomSujet() + "'" +
            ", customContenu='" + getCustomContenu() + "'" +
            ", customUrl='" + getCustomUrl() + "'" +
            "}";
    }
}
