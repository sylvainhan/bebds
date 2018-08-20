package com.acoss.webae.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.acoss.webae.domain.enumeration.EtatDAvancement;
import com.acoss.webae.domain.enumeration.TypeDeDemande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO for the Demande entity.
 */
public class DemandeDTO implements Serializable {

    private Long id;

    private EtatDAvancement etatDAvance;

    private TypeDeDemande typeDemande;

    private Long userPreferenceId;

    private String userPreferenceNumCompteExterne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatDAvancement getEtatDAvance() {
        return etatDAvance;
    }

    public void setEtatDAvance(EtatDAvancement etatDAvance) {
        this.etatDAvance = etatDAvance;
    }

    public TypeDeDemande getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(TypeDeDemande typeDemande) {
        this.typeDemande = typeDemande;
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

        DemandeDTO demandeDTO = (DemandeDTO) o;
        if(demandeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandeDTO{" +
            "id=" + getId() +
            ", etatDAvance='" + getEtatDAvance() + "'" +
            ", typeDemande='" + getTypeDemande() + "'" +
            "}";
    }
}
