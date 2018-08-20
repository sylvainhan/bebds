package com.acoss.webae.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.NotifChannel;

import com.acoss.webae.domain.enumeration.NotifMoment;

/**
 * A PreferenceNotif.
 */
@Entity
@Table(name = "preference_notif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreferenceNotif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private NotifChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "moment")
    private NotifMoment moment;

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

    public Boolean isActive() {
        return active;
    }

    public PreferenceNotif active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NotifChannel getChannel() {
        return channel;
    }

    public PreferenceNotif channel(NotifChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(NotifChannel channel) {
        this.channel = channel;
    }

    public NotifMoment getMoment() {
        return moment;
    }

    public PreferenceNotif moment(NotifMoment moment) {
        this.moment = moment;
        return this;
    }

    public void setMoment(NotifMoment moment) {
        this.moment = moment;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public PreferenceNotif userPreference(UserPreference userPreference) {
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
        PreferenceNotif preferenceNotif = (PreferenceNotif) o;
        if (preferenceNotif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preferenceNotif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreferenceNotif{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", channel='" + getChannel() + "'" +
            ", moment='" + getMoment() + "'" +
            "}";
    }
}
