import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    LastConnectionWebaeService,
    LastConnectionWebaePopupService,
    LastConnectionWebaeComponent,
    LastConnectionWebaeDetailComponent,
    LastConnectionWebaeDialogComponent,
    LastConnectionWebaePopupComponent,
    LastConnectionWebaeDeletePopupComponent,
    LastConnectionWebaeDeleteDialogComponent,
    lastConnectionRoute,
    lastConnectionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lastConnectionRoute,
    ...lastConnectionPopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LastConnectionWebaeComponent,
        LastConnectionWebaeDetailComponent,
        LastConnectionWebaeDialogComponent,
        LastConnectionWebaeDeleteDialogComponent,
        LastConnectionWebaePopupComponent,
        LastConnectionWebaeDeletePopupComponent,
    ],
    entryComponents: [
        LastConnectionWebaeComponent,
        LastConnectionWebaeDialogComponent,
        LastConnectionWebaePopupComponent,
        LastConnectionWebaeDeleteDialogComponent,
        LastConnectionWebaeDeletePopupComponent,
    ],
    providers: [
        LastConnectionWebaeService,
        LastConnectionWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeLastConnectionWebaeModule {}
