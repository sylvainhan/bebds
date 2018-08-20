import { BaseEntity } from './../../shared';

export const enum TypeDevice {
    'IOS',
    'ANDROID',
    'WEB'
}

export class UserDeviceWebae implements BaseEntity {
    constructor(
        public id?: number,
        public deviceToken?: string,
        public typeDevice?: TypeDevice,
        public userPreferenceNumCompteExterne?: string,
        public userPreferenceId?: number,
    ) {
    }
}
