package com.acoss.webae.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.TypeDevice;

/**
 * A UserDevice.
 */
@Entity
@Table(name = "user_device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "device_token")
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_device")
    private TypeDevice typeDevice;

    @ManyToOne
    @JoinColumn(name="user_and_preference_id")
    private UserPreference userPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public UserDevice deviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
        return this;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public UserDevice typeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
        return this;
    }

    public void setTypeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public UserDevice userPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
        return this;
    }

    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDevice userDevice = (UserDevice) o;
        if (userDevice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDevice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDevice{" +
            "id=" + getId() +
            ", deviceToken='" + getDeviceToken() + "'" +
            ", typeDevice='" + getTypeDevice() + "'" +
            "}";
    }
}
