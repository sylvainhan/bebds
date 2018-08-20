import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DemandeWebaeComponent } from './demande-webae.component';
import { DemandeWebaeDetailComponent } from './demande-webae-detail.component';
import { DemandeWebaePopupComponent } from './demande-webae-dialog.component';
import { DemandeWebaeDeletePopupComponent } from './demande-webae-delete-dialog.component';

export const demandeRoute: Routes = [
    {
        path: 'demande-webae',
        component: DemandeWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Demandes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'demande-webae/:id',
        component: DemandeWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Demandes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const demandePopupRoute: Routes = [
    {
        path: 'demande-webae-new',
        component: DemandeWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Demandes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'demande-webae/:id/edit',
        component: DemandeWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Demandes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'demande-webae/:id/delete',
        component: DemandeWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Demandes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
