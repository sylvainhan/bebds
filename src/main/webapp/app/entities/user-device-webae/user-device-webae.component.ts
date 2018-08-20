import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserDeviceWebae } from './user-device-webae.model';
import { UserDeviceWebaeService } from './user-device-webae.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-device-webae',
    templateUrl: './user-device-webae.component.html'
})
export class UserDeviceWebaeComponent implements OnInit, OnDestroy {
userDevices: UserDeviceWebae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userDeviceService: UserDeviceWebaeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userDeviceService.query().subscribe(
            (res: HttpResponse<UserDeviceWebae[]>) => {
                this.userDevices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserDevices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserDeviceWebae) {
        return item.id;
    }
    registerChangeInUserDevices() {
        this.eventSubscriber = this.eventManager.subscribe('userDeviceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
