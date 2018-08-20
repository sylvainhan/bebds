import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PreferenceNotifWebaeComponent } from './preference-notif-webae.component';
import { PreferenceNotifWebaeDetailComponent } from './preference-notif-webae-detail.component';
import { PreferenceNotifWebaePopupComponent } from './preference-notif-webae-dialog.component';
import { PreferenceNotifWebaeDeletePopupComponent } from './preference-notif-webae-delete-dialog.component';

export const preferenceNotifRoute: Routes = [
    {
        path: 'preference-notif-webae',
        component: PreferenceNotifWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PreferenceNotifs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'preference-notif-webae/:id',
        component: PreferenceNotifWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PreferenceNotifs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const preferenceNotifPopupRoute: Routes = [
    {
        path: 'preference-notif-webae-new',
        component: PreferenceNotifWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PreferenceNotifs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'preference-notif-webae/:id/edit',
        component: PreferenceNotifWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PreferenceNotifs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'preference-notif-webae/:id/delete',
        component: PreferenceNotifWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PreferenceNotifs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
