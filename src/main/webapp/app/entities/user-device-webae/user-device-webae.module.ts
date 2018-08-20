import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    UserDeviceWebaeService,
    UserDeviceWebaePopupService,
    UserDeviceWebaeComponent,
    UserDeviceWebaeDetailComponent,
    UserDeviceWebaeDialogComponent,
    UserDeviceWebaePopupComponent,
    UserDeviceWebaeDeletePopupComponent,
    UserDeviceWebaeDeleteDialogComponent,
    userDeviceRoute,
    userDevicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...userDeviceRoute,
    ...userDevicePopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserDeviceWebaeComponent,
        UserDeviceWebaeDetailComponent,
        UserDeviceWebaeDialogComponent,
        UserDeviceWebaeDeleteDialogComponent,
        UserDeviceWebaePopupComponent,
        UserDeviceWebaeDeletePopupComponent,
    ],
    entryComponents: [
        UserDeviceWebaeComponent,
        UserDeviceWebaeDialogComponent,
        UserDeviceWebaePopupComponent,
        UserDeviceWebaeDeleteDialogComponent,
        UserDeviceWebaeDeletePopupComponent,
    ],
    providers: [
        UserDeviceWebaeService,
        UserDeviceWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeUserDeviceWebaeModule {}
