import { BaseEntity } from './../../shared';

export const enum PreferenceAvatar {
    'PREFERENCEAVATAR'
}

export const enum PreferenceAbonnement {
    'PREFERENCEABONNEMENT'
}

export class UserPreferenceWebae implements BaseEntity {
    constructor(
        public id?: number,
        public numCompteExterne?: string,
        public avatarPref?: PreferenceAvatar,
        public abonnePref?: PreferenceAbonnement,
        public userDevices?: BaseEntity[],
        public lastConnections?: BaseEntity[],
        public demandes?: BaseEntity[],
        public notifications?: BaseEntity[],
        public preferenceNotifs?: BaseEntity[],
    ) {
    }
}
