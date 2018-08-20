package com.acoss.webae.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.NotifChannel;
import com.acoss.webae.domain.enumeration.NotifMoment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the PreferenceNotif entity.
 */
public class PreferenceNotifDTO implements Serializable {

    private Long id;

    private Boolean active;

    private NotifChannel channel;

    @JsonProperty("type")
    private NotifMoment moment;

    private Long userPreferenceId;

    private String numCompteExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NotifChannel getChannel() {
        return channel;
    }

    public void setChannel(NotifChannel channel) {
        this.channel = channel;
    }

    public NotifMoment getMoment() {
        return moment;
    }

    public void setMoment(NotifMoment moment) {
        this.moment = moment;
    }

    @JsonIgnore
    public Long getUserPreferenceId() {
        return userPreferenceId;
    }

    @JsonProperty
    public void setUserPreferenceId(Long userPreferenceId) {
        this.userPreferenceId = userPreferenceId;
    }

    @JsonIgnore
    public String getNumCompteExterne() {
        return numCompteExterne;
    }

    @JsonProperty
    public void setNumCompteExterne(String numCompteExterne) {
        this.numCompteExterne = numCompteExterne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreferenceNotifDTO preferenceNotifDTO = (PreferenceNotifDTO) o;
        if(preferenceNotifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preferenceNotifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreferenceNotifDTO{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", channel='" + getChannel() + "'" +
            ", moment='" + getMoment() + "'" +
            "}";
    }
}
