import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LastConnectionWebaeComponent } from './last-connection-webae.component';
import { LastConnectionWebaeDetailComponent } from './last-connection-webae-detail.component';
import { LastConnectionWebaePopupComponent } from './last-connection-webae-dialog.component';
import { LastConnectionWebaeDeletePopupComponent } from './last-connection-webae-delete-dialog.component';

export const lastConnectionRoute: Routes = [
    {
        path: 'last-connection-webae',
        component: LastConnectionWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LastConnections'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'last-connection-webae/:id',
        component: LastConnectionWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LastConnections'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lastConnectionPopupRoute: Routes = [
    {
        path: 'last-connection-webae-new',
        component: LastConnectionWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LastConnections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'last-connection-webae/:id/edit',
        component: LastConnectionWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LastConnections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'last-connection-webae/:id/delete',
        component: LastConnectionWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LastConnections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
