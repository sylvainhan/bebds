package com.acoss.webae.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.PreferenceAvatar;

import com.acoss.webae.domain.enumeration.PreferenceAbonnement;

/**
 * A UserPreference.
 */
@Entity
@Table(name = "user_and_preference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "num_compte_externe")
    private String numCompteExterne;

    @Enumerated(EnumType.STRING)
    @Column(name = "avatar_pref")
    private PreferenceAvatar avatarPref;

    @Enumerated(EnumType.STRING)
    @Column(name = "abonne_pref")
    private PreferenceAbonnement abonnePref;

    @OneToMany(mappedBy = "userPreference")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserDevice> userDevices = new HashSet<>();

    @OneToMany(mappedBy = "userPreference")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LastConnection> lastConnections = new HashSet<>();

    @OneToMany(mappedBy = "userPreference")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Demande> demandes = new HashSet<>();

    @OneToMany(mappedBy = "userPreference")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "userPreference", cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreferenceNotif> preferenceNotifs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCompteExterne() {
        return numCompteExterne;
    }

    public UserPreference numCompteExterne(String numCompteExterne) {
        this.numCompteExterne = numCompteExterne;
        return this;
    }

    public void setNumCompteExterne(String numCompteExterne) {
        this.numCompteExterne = numCompteExterne;
    }

    public PreferenceAvatar getAvatarPref() {
        return avatarPref;
    }

    public UserPreference avatarPref(PreferenceAvatar avatarPref) {
        this.avatarPref = avatarPref;
        return this;
    }

    public void setAvatarPref(PreferenceAvatar avatarPref) {
        this.avatarPref = avatarPref;
    }

    public PreferenceAbonnement getAbonnePref() {
        return abonnePref;
    }

    public UserPreference abonnePref(PreferenceAbonnement abonnePref) {
        this.abonnePref = abonnePref;
        return this;
    }

    public void setAbonnePref(PreferenceAbonnement abonnePref) {
        this.abonnePref = abonnePref;
    }

    public Set<UserDevice> getUserDevices() {
        return userDevices;
    }

    public UserPreference userDevices(Set<UserDevice> userDevices) {
        this.userDevices = userDevices;
        return this;
    }

    public UserPreference addUserDevice(UserDevice userDevice) {
        this.userDevices.add(userDevice);
        userDevice.setUserPreference(this);
        return this;
    }

    public UserPreference removeUserDevice(UserDevice userDevice) {
        this.userDevices.remove(userDevice);
        userDevice.setUserPreference(null);
        return this;
    }

    public void setUserDevices(Set<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public Set<LastConnection> getLastConnections() {
        return lastConnections;
    }

    public UserPreference lastConnections(Set<LastConnection> lastConnections) {
        this.lastConnections = lastConnections;
        return this;
    }

    public UserPreference addLastConnection(LastConnection lastConnection) {
        this.lastConnections.add(lastConnection);
        lastConnection.setUserPreference(this);
        return this;
    }

    public UserPreference removeLastConnection(LastConnection lastConnection) {
        this.lastConnections.remove(lastConnection);
        lastConnection.setUserPreference(null);
        return this;
    }

    public void setLastConnections(Set<LastConnection> lastConnections) {
        this.lastConnections = lastConnections;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    public UserPreference demandes(Set<Demande> demandes) {
        this.demandes = demandes;
        return this;
    }

    public UserPreference addDemande(Demande demande) {
        this.demandes.add(demande);
        demande.setUserPreference(this);
        return this;
    }

    public UserPreference removeDemande(Demande demande) {
        this.demandes.remove(demande);
        demande.setUserPreference(null);
        return this;
    }

    public void setDemandes(Set<Demande> demandes) {
        this.demandes = demandes;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public UserPreference notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public UserPreference addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUserPreference(this);
        return this;
    }

    public UserPreference removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUserPreference(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<PreferenceNotif> getPreferenceNotifs() {
        return preferenceNotifs;
    }

    public UserPreference preferenceNotifs(Set<PreferenceNotif> preferenceNotifs) {
        this.preferenceNotifs = preferenceNotifs;
        return this;
    }

    public UserPreference addPreferenceNotif(PreferenceNotif preferenceNotif) {
        this.preferenceNotifs.add(preferenceNotif);
        preferenceNotif.setUserPreference(this);
        return this;
    }

    public UserPreference removePreferenceNotif(PreferenceNotif preferenceNotif) {
        this.preferenceNotifs.remove(preferenceNotif);
        preferenceNotif.setUserPreference(null);
        return this;
    }

    public void setPreferenceNotifs(Set<PreferenceNotif> preferenceNotifs) {
        this.preferenceNotifs = preferenceNotifs;
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
        UserPreference userPreference = (UserPreference) o;
        if (userPreference.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPreference.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPreference{" +
            "id=" + getId() +
            ", numCompteExterne='" + getNumCompteExterne() + "'" +
            ", avatarPref='" + getAvatarPref() + "'" +
            ", abonnePref='" + getAbonnePref() + "'" +
            "}";
    }
}
