import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserPreferenceWebae } from './user-and-preference-webae.model';
import { UserPreferenceWebaeService } from './user-and-preference-webae.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-and-preference-webae',
    templateUrl: './user-and-preference-webae.component.html'
})
export class UserPreferenceWebaeComponent implements OnInit, OnDestroy {
userPreferences: UserPreferenceWebae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userPreferenceService: UserPreferenceWebaeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userPreferenceService.query().subscribe(
            (res: HttpResponse<UserPreferenceWebae[]>) => {
                this.userPreferences = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserPreferences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserPreferenceWebae) {
        return item.id;
    }
    registerChangeInUserPreferences() {
        this.eventSubscriber = this.eventManager.subscribe('userPreferenceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
