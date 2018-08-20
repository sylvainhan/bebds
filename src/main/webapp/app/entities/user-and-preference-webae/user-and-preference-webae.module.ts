import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    UserPreferenceWebaeService,
    UserPreferenceWebaePopupService,
    UserPreferenceWebaeComponent,
    UserPreferenceWebaeDetailComponent,
    UserPreferenceWebaeDialogComponent,
    UserPreferenceWebaePopupComponent,
    UserPreferenceWebaeDeletePopupComponent,
    UserPreferenceWebaeDeleteDialogComponent,
    userPreferenceRoute,
    userPreferencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...userPreferenceRoute,
    ...userPreferencePopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserPreferenceWebaeComponent,
        UserPreferenceWebaeDetailComponent,
        UserPreferenceWebaeDialogComponent,
        UserPreferenceWebaeDeleteDialogComponent,
        UserPreferenceWebaePopupComponent,
        UserPreferenceWebaeDeletePopupComponent,
    ],
    entryComponents: [
        UserPreferenceWebaeComponent,
        UserPreferenceWebaeDialogComponent,
        UserPreferenceWebaePopupComponent,
        UserPreferenceWebaeDeleteDialogComponent,
        UserPreferenceWebaeDeletePopupComponent,
    ],
    providers: [
        UserPreferenceWebaeService,
        UserPreferenceWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeUserPreferenceWebaeModule {}
