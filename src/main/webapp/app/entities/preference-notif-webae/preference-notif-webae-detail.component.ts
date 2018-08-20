import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { PreferenceNotifWebaeService } from './preference-notif-webae.service';

@Component({
    selector: 'jhi-preference-notif-webae-detail',
    templateUrl: './preference-notif-webae-detail.component.html'
})
export class PreferenceNotifWebaeDetailComponent implements OnInit, OnDestroy {

    preferenceNotif: PreferenceNotifWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private preferenceNotifService: PreferenceNotifWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPreferenceNotifs();
    }

    load(id) {
        this.preferenceNotifService.find(id)
            .subscribe((preferenceNotifResponse: HttpResponse<PreferenceNotifWebae>) => {
                this.preferenceNotif = preferenceNotifResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPreferenceNotifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'preferenceNotifListModification',
            (response) => this.load(this.preferenceNotif.id)
        );
    }
}
