import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { PreferenceNotifWebaeService } from './preference-notif-webae.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-preference-notif-webae',
    templateUrl: './preference-notif-webae.component.html'
})
export class PreferenceNotifWebaeComponent implements OnInit, OnDestroy {
preferenceNotifs: PreferenceNotifWebae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private preferenceNotifService: PreferenceNotifWebaeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.preferenceNotifService.query().subscribe(
            (res: HttpResponse<PreferenceNotifWebae[]>) => {
                this.preferenceNotifs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPreferenceNotifs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PreferenceNotifWebae) {
        return item.id;
    }
    registerChangeInPreferenceNotifs() {
        this.eventSubscriber = this.eventManager.subscribe('preferenceNotifListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
