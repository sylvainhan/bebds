import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserDeviceWebaeComponent } from './user-device-webae.component';
import { UserDeviceWebaeDetailComponent } from './user-device-webae-detail.component';
import { UserDeviceWebaePopupComponent } from './user-device-webae-dialog.component';
import { UserDeviceWebaeDeletePopupComponent } from './user-device-webae-delete-dialog.component';

export const userDeviceRoute: Routes = [
    {
        path: 'user-device-webae',
        component: UserDeviceWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDevices'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-device-webae/:id',
        component: UserDeviceWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDevices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userDevicePopupRoute: Routes = [
    {
        path: 'user-device-webae-new',
        component: UserDeviceWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDevices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-device-webae/:id/edit',
        component: UserDeviceWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDevices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-device-webae/:id/delete',
        component: UserDeviceWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserDevices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
