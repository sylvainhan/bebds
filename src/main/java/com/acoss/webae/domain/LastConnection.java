package com.acoss.webae.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.TypeDevice;

/**
 * A LastConnection.
 */
@Entity
@Table(name = "last_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LastConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "actuel_connection")
    private ZonedDateTime actuelConnection;

    @Column(name = "last_connection")
    private ZonedDateTime lastConnection;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_deveice")
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

    public ZonedDateTime getActuelConnection() {
        return actuelConnection;
    }

    public LastConnection actuelConnection(ZonedDateTime actuelConnection) {
        this.actuelConnection = actuelConnection;
        return this;
    }

    public void setActuelConnection(ZonedDateTime actuelConnection) {
        this.actuelConnection = actuelConnection;
    }

    public ZonedDateTime getLastConnection() {
        return lastConnection;
    }

    public LastConnection lastConnection(ZonedDateTime lastConnection) {
        this.lastConnection = lastConnection;
        return this;
    }

    public void setLastConnection(ZonedDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public LastConnection typeDeveice(TypeDevice typeDeveice) {
        this.typeDevice = typeDeveice;
        return this;
    }

    public void setTypeDevice(TypeDevice typeDevice) {
        this.typeDevice = typeDevice;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public LastConnection userPreference(UserPreference userPreference) {
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
        LastConnection lastConnection = (LastConnection) o;
        if (lastConnection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lastConnection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LastConnection{" +
            "id=" + getId() +
            ", actuelConnection='" + getActuelConnection() + "'" +
            ", lastConnection='" + getLastConnection() + "'" +
            ", typeDevice='" + getTypeDevice() + "'" +
            "}";
    }
}
