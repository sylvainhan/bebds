package com.acoss.webae.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.TypeDevice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the LastConnection entity.
 */
public class LastConnectionDTO implements Serializable {

    private Long id;

    private ZonedDateTime actuelConnection;

    private ZonedDateTime lastConnection;

    private TypeDevice typeDeveice;

    private Long userPreferenceId;

    private String userPreferenceNumCompteExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getActuelConnection() {
        return actuelConnection;
    }

    public void setActuelConnection(ZonedDateTime actuelConnection) {
        this.actuelConnection = actuelConnection;
    }

    public ZonedDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(ZonedDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public TypeDevice getTypeDeveice() {
        return typeDeveice;
    }

    public void setTypeDeveice(TypeDevice typeDeveice) {
        this.typeDeveice = typeDeveice;
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

        LastConnectionDTO lastConnectionDTO = (LastConnectionDTO) o;
        if(lastConnectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lastConnectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LastConnectionDTO{" +
            "id=" + getId() +
            ", actuelConnection='" + getActuelConnection() + "'" +
            ", lastConnection='" + getLastConnection() + "'" +
            ", typeDeveice='" + getTypeDeveice() + "'" +
            "}";
    }
}
