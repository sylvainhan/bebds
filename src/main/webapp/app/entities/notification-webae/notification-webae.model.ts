import { BaseEntity } from './../../shared';

export const enum TypeDeNotif {
    'BIENVENUE',
    'ADHESION',
    'ECHEANCE',
    'MESSAGE',
    'CONSEILS',
    'ACTUALITES',
    'DOCUMENT',
    'DEMARCHE',
    'NOTIF_ACCRE',
    'ALERT_ACCRE'
}

export class NotificationWebae implements BaseEntity {
    constructor(
        public id?: number,
        public estLu?: boolean,
        public type?: TypeDeNotif,
        public customSujet?: string,
        public customContenu?: string,
        public customUrl?: string,
        public userPreferenceNumCompteExterne?: string,
        public userPreferenceId?: number,
    ) {
        this.estLu = false;
    }
}
