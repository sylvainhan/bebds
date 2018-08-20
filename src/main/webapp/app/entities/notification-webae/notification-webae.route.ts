import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NotificationWebaeComponent } from './notification-webae.component';
import { NotificationWebaeDetailComponent } from './notification-webae-detail.component';
import { NotificationWebaePopupComponent } from './notification-webae-dialog.component';
import { NotificationWebaeDeletePopupComponent } from './notification-webae-delete-dialog.component';

export const notificationRoute: Routes = [
    {
        path: 'notification-webae',
        component: NotificationWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notifications'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'notification-webae/:id',
        component: NotificationWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notifications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notificationPopupRoute: Routes = [
    {
        path: 'notification-webae-new',
        component: NotificationWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notifications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification-webae/:id/edit',
        component: NotificationWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notifications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification-webae/:id/delete',
        component: NotificationWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Notifications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
