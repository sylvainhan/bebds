package com.acoss.webae.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.TypeDeNotif;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "est_lu")
    private Boolean estLu;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypeDeNotif type;

    @Column(name = "custom_sujet")
    private String customSujet;

    @Column(name = "custom_contenu")
    private String customContenu;

    @Column(name = "custom_url")
    private String customUrl;

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

    public Boolean isEstLu() {
        return estLu;
    }

    public Notification estLu(Boolean estLu) {
        this.estLu = estLu;
        return this;
    }

    public void setEstLu(Boolean estLu) {
        this.estLu = estLu;
    }

    public TypeDeNotif getType() {
        return type;
    }

    public Notification type(TypeDeNotif type) {
        this.type = type;
        return this;
    }

    public void setType(TypeDeNotif type) {
        this.type = type;
    }

    public String getCustomSujet() {
        return customSujet;
    }

    public Notification customSujet(String customSujet) {
        this.customSujet = customSujet;
        return this;
    }

    public void setCustomSujet(String customSujet) {
        this.customSujet = customSujet;
    }

    public String getCustomContenu() {
        return customContenu;
    }

    public Notification customContenu(String customContenu) {
        this.customContenu = customContenu;
        return this;
    }

    public void setCustomContenu(String customContenu) {
        this.customContenu = customContenu;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public Notification customUrl(String customUrl) {
        this.customUrl = customUrl;
        return this;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public Notification userPreference(UserPreference userPreference) {
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
        Notification notification = (Notification) o;
        if (notification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", estLu='" + isEstLu() + "'" +
            ", type='" + getType() + "'" +
            ", customSujet='" + getCustomSujet() + "'" +
            ", customContenu='" + getCustomContenu() + "'" +
            ", customUrl='" + getCustomUrl() + "'" +
            "}";
    }
}
