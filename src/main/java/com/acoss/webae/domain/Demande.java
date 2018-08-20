package com.acoss.webae.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.acoss.webae.domain.enumeration.EtatDAvancement;

import com.acoss.webae.domain.enumeration.TypeDeDemande;

/**
 * A Demande.
 */
@Entity
@Table(name = "demande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_d_avance")
    private EtatDAvancement etatDAvance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_demande")
    private TypeDeDemande typeDemande;

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

    public EtatDAvancement getEtatDAvance() {
        return etatDAvance;
    }

    public Demande etatDAvance(EtatDAvancement etatDAvance) {
        this.etatDAvance = etatDAvance;
        return this;
    }

    public void setEtatDAvance(EtatDAvancement etatDAvance) {
        this.etatDAvance = etatDAvance;
    }

    public TypeDeDemande getTypeDemande() {
        return typeDemande;
    }

    public Demande typeDemande(TypeDeDemande typeDemande) {
        this.typeDemande = typeDemande;
        return this;
    }

    public void setTypeDemande(TypeDeDemande typeDemande) {
        this.typeDemande = typeDemande;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public Demande userPreference(UserPreference userPreference) {
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
        Demande demande = (Demande) o;
        if (demande.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demande.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Demande{" +
            "id=" + getId() +
            ", etatDAvance='" + getEtatDAvance() + "'" +
            ", typeDemande='" + getTypeDemande() + "'" +
            "}";
    }
}
