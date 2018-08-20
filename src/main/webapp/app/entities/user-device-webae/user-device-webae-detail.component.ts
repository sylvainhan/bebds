import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserDeviceWebae } from './user-device-webae.model';
import { UserDeviceWebaeService } from './user-device-webae.service';

@Component({
    selector: 'jhi-user-device-webae-detail',
    templateUrl: './user-device-webae-detail.component.html'
})
export class UserDeviceWebaeDetailComponent implements OnInit, OnDestroy {

    userDevice: UserDeviceWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userDeviceService: UserDeviceWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserDevices();
    }

    load(id) {
        this.userDeviceService.find(id)
            .subscribe((userDeviceResponse: HttpResponse<UserDeviceWebae>) => {
                this.userDevice = userDeviceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserDevices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userDeviceListModification',
            (response) => this.load(this.userDevice.id)
        );
    }
}
