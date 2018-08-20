import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserPreferenceWebae } from './user-and-preference-webae.model';
import { UserPreferenceWebaeService } from './user-and-preference-webae.service';

@Component({
    selector: 'jhi-user-and-preference-webae-detail',
    templateUrl: './user-and-preference-webae-detail.component.html'
})
export class UserPreferenceWebaeDetailComponent implements OnInit, OnDestroy {

    userPreference: UserPreferenceWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userPreferenceService: UserPreferenceWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserPreferences();
    }

    load(id) {
        this.userPreferenceService.find(id)
            .subscribe((userPreferenceResponse: HttpResponse<UserPreferenceWebae>) => {
                this.userPreference = userPreferenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserPreferences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userPreferenceListModification',
            (response) => this.load(this.userPreference.id)
        );
    }
}
