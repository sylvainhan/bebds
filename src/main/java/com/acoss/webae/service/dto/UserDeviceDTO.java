package com.acoss.webae.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.TypeDevice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

/**
 * A DTO for the UserDevice entity.
 */
public class UserDeviceDTO implements Serializable {

    private Long id;

    @NotNull(message = "'device token' is mandatory")
    private String deviceToken;

    private TypeDevice typeDevice;

    private Long userPreferenceId;

    //@Null(message = "'numéro compte externe' is getting from access_token")
    private String numCompteExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
    }

    @JsonIgnore
    public Long getUserPreferenceId() {
        return userPreferenceId;
    }

    @JsonProperty
    public void setUserPreferenceId(Long userPreferenceId) {
        this.userPreferenceId = userPreferenceId;
    }

    @JsonProperty
    public String getNumCompteExterne() {
        return numCompteExterne;
    }

    @JsonIgnore
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

        UserDeviceDTO userDeviceDTO = (UserDeviceDTO) o;
        if(userDeviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDeviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDeviceDTO{" +
            "id=" + getId() +
            ", deviceToken='" + getDeviceToken() + "'" +
            ", typeDevice='" + getTypeDevice() + "'" +
            "}";
    }
}
