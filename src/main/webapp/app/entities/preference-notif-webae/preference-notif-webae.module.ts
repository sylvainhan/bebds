import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    PreferenceNotifWebaeService,
    PreferenceNotifWebaePopupService,
    PreferenceNotifWebaeComponent,
    PreferenceNotifWebaeDetailComponent,
    PreferenceNotifWebaeDialogComponent,
    PreferenceNotifWebaePopupComponent,
    PreferenceNotifWebaeDeletePopupComponent,
    PreferenceNotifWebaeDeleteDialogComponent,
    preferenceNotifRoute,
    preferenceNotifPopupRoute,
} from './';

const ENTITY_STATES = [
    ...preferenceNotifRoute,
    ...preferenceNotifPopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PreferenceNotifWebaeComponent,
        PreferenceNotifWebaeDetailComponent,
        PreferenceNotifWebaeDialogComponent,
        PreferenceNotifWebaeDeleteDialogComponent,
        PreferenceNotifWebaePopupComponent,
        PreferenceNotifWebaeDeletePopupComponent,
    ],
    entryComponents: [
        PreferenceNotifWebaeComponent,
        PreferenceNotifWebaeDialogComponent,
        PreferenceNotifWebaePopupComponent,
        PreferenceNotifWebaeDeleteDialogComponent,
        PreferenceNotifWebaeDeletePopupComponent,
    ],
    providers: [
        PreferenceNotifWebaeService,
        PreferenceNotifWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaePreferenceNotifWebaeModule {}
