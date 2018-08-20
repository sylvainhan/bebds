import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WebaeTauxModule } from './taux/taux.module';
import { WebaeUserDeviceWebaeModule } from './user-device-webae/user-device-webae.module';
import { WebaeLastConnectionWebaeModule } from './last-connection-webae/last-connection-webae.module';
import { WebaeNotificationWebaeModule } from './notification-webae/notification-webae.module';
import { WebaeDemandeWebaeModule } from './demande-webae/demande-webae.module';
import { WebaePreferenceNotifWebaeModule } from './preference-notif-webae/preference-notif-webae.module';
import { WebaeUserPreferenceWebaeModule } from './user-and-preference-webae/user-and-preference-webae.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WebaeTauxModule,
        WebaeUserDeviceWebaeModule,
        WebaeLastConnectionWebaeModule,
        WebaeNotificationWebaeModule,
        WebaeDemandeWebaeModule,
        WebaePreferenceNotifWebaeModule,
        WebaeUserPreferenceWebaeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeEntityModule {}
