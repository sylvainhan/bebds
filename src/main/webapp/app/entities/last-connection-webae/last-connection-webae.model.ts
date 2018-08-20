import { BaseEntity } from './../../shared';

export const enum TypeDevice {
    'IOS',
    'ANDROID',
    'WEB'
}

export class LastConnectionWebae implements BaseEntity {
    constructor(
        public id?: number,
        public actuelConnection?: any,
        public lastConnection?: any,
        public typeDeveice?: TypeDevice,
        public userPreferenceNumCompteExterne?: string,
        public userPreferenceId?: number,
    ) {
    }
}
