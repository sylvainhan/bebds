import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TauxComponent } from './taux.component';
import { TauxDetailComponent } from './taux-detail.component';
import { TauxPopupComponent } from './taux-dialog.component';
import { TauxDeletePopupComponent } from './taux-delete-dialog.component';

export const tauxRoute: Routes = [
    {
        path: 'taux',
        component: TauxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tauxes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'taux/:id',
        component: TauxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tauxes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tauxPopupRoute: Routes = [
    {
        path: 'taux-new',
        component: TauxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tauxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'taux/:id/edit',
        component: TauxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tauxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'taux/:id/delete',
        component: TauxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tauxes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
