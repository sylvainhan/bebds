import { BaseEntity } from './../../shared';

export const enum EtatDAvancement {
    'ENVOYEE',
    'EN_COURS',
    'EN_ATTENTE',
    'FINALISEE',
    'PROBLEM'
}

export const enum TypeDeDemande {
    'ADHESION_STATUT',
    'SOUSCRIPTION_ACCRE',
    'EIRL',
    'MODIFICATION_ACTIVITE',
    'CESSATION_ACTIVITE',
    'DELAI_PAIEMENT',
    'FORMATION'
}

export class DemandeWebae implements BaseEntity {
    constructor(
        public id?: number,
        public etatDAvance?: EtatDAvancement,
        public typeDemande?: TypeDeDemande,
        public userPreferenceNumCompteExterne?: string,
        public userPreferenceId?: number,
    ) {
    }
}
