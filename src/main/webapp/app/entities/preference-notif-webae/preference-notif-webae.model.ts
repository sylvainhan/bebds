import { BaseEntity } from './../../shared';

export const enum NotifChannel {
    'SMS',
    'EMAIL',
    'NOTIF_PHONE',
    'NOTIF_TABLET'
}

export const enum NotifMoment {
    'B3_OUV_DECL',
    'B3_FER_DECL'
}

export class PreferenceNotifWebae implements BaseEntity {
    constructor(
        public id?: number,
        public active?: boolean,
        public channel?: NotifChannel,
        public moment?: NotifMoment,
        public userPreferenceNumCompteExterne?: string,
        public userPreferenceId?: number,
    ) {
        this.active = false;
    }
}
