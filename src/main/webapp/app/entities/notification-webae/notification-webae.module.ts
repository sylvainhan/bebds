import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    NotificationWebaeService,
    NotificationWebaePopupService,
    NotificationWebaeComponent,
    NotificationWebaeDetailComponent,
    NotificationWebaeDialogComponent,
    NotificationWebaePopupComponent,
    NotificationWebaeDeletePopupComponent,
    NotificationWebaeDeleteDialogComponent,
    notificationRoute,
    notificationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...notificationRoute,
    ...notificationPopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NotificationWebaeComponent,
        NotificationWebaeDetailComponent,
        NotificationWebaeDialogComponent,
        NotificationWebaeDeleteDialogComponent,
        NotificationWebaePopupComponent,
        NotificationWebaeDeletePopupComponent,
    ],
    entryComponents: [
        NotificationWebaeComponent,
        NotificationWebaeDialogComponent,
        NotificationWebaePopupComponent,
        NotificationWebaeDeleteDialogComponent,
        NotificationWebaeDeletePopupComponent,
    ],
    providers: [
        NotificationWebaeService,
        NotificationWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeNotificationWebaeModule {}
