import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    TauxService,
    TauxPopupService,
    TauxComponent,
    TauxDetailComponent,
    TauxDialogComponent,
    TauxPopupComponent,
    TauxDeletePopupComponent,
    TauxDeleteDialogComponent,
    tauxRoute,
    tauxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tauxRoute,
    ...tauxPopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TauxComponent,
        TauxDetailComponent,
        TauxDialogComponent,
        TauxDeleteDialogComponent,
        TauxPopupComponent,
        TauxDeletePopupComponent,
    ],
    entryComponents: [
        TauxComponent,
        TauxDialogComponent,
        TauxPopupComponent,
        TauxDeleteDialogComponent,
        TauxDeletePopupComponent,
    ],
    providers: [
        TauxService,
        TauxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeTauxModule {}
