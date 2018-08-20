import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebaeSharedModule } from '../../shared';
import {
    DemandeWebaeService,
    DemandeWebaePopupService,
    DemandeWebaeComponent,
    DemandeWebaeDetailComponent,
    DemandeWebaeDialogComponent,
    DemandeWebaePopupComponent,
    DemandeWebaeDeletePopupComponent,
    DemandeWebaeDeleteDialogComponent,
    demandeRoute,
    demandePopupRoute,
} from './';

const ENTITY_STATES = [
    ...demandeRoute,
    ...demandePopupRoute,
];

@NgModule({
    imports: [
        WebaeSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DemandeWebaeComponent,
        DemandeWebaeDetailComponent,
        DemandeWebaeDialogComponent,
        DemandeWebaeDeleteDialogComponent,
        DemandeWebaePopupComponent,
        DemandeWebaeDeletePopupComponent,
    ],
    entryComponents: [
        DemandeWebaeComponent,
        DemandeWebaeDialogComponent,
        DemandeWebaePopupComponent,
        DemandeWebaeDeleteDialogComponent,
        DemandeWebaeDeletePopupComponent,
    ],
    providers: [
        DemandeWebaeService,
        DemandeWebaePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebaeDemandeWebaeModule {}
