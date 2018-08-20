import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserPreferenceWebaeComponent } from './user-and-preference-webae.component';
import { UserPreferenceWebaeDetailComponent } from './user-and-preference-webae-detail.component';
import { UserPreferenceWebaePopupComponent } from './user-and-preference-webae-dialog.component';
import { UserPreferenceWebaeDeletePopupComponent } from './user-and-preference-webae-delete-dialog.component';

export const userPreferenceRoute: Routes = [
    {
        path: 'user-and-preference-webae',
        component: UserPreferenceWebaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPreferences'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-and-preference-webae/:id',
        component: UserPreferenceWebaeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPreferences'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPreferencePopupRoute: Routes = [
    {
        path: 'user-and-preference-webae-new',
        component: UserPreferenceWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-and-preference-webae/:id/edit',
        component: UserPreferenceWebaePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-and-preference-webae/:id/delete',
        component: UserPreferenceWebaeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
