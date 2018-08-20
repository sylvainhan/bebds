import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserDeviceWebae } from './user-device-webae.model';
import { UserDeviceWebaePopupService } from './user-device-webae-popup.service';
import { UserDeviceWebaeService } from './user-device-webae.service';
import { UserPreferenceWebae, UserPreferenceWebaeService } from '../user-and-preference-webae';

@Component({
    selector: 'jhi-user-device-webae-dialog',
    templateUrl: './user-device-webae-dialog.component.html'
})
export class UserDeviceWebaeDialogComponent implements OnInit {

    userDevice: UserDeviceWebae;
    isSaving: boolean;

    userandpreferences: UserPreferenceWebae[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userDeviceService: UserDeviceWebaeService,
        private userPreferenceService: UserPreferenceWebaeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userPreferenceService.query()
            .subscribe((res: HttpResponse<UserPreferenceWebae[]>) => { this.userandpreferences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userDevice.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userDeviceService.update(this.userDevice));
        } else {
            this.subscribeToSaveResponse(
                this.userDeviceService.create(this.userDevice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserDeviceWebae>>) {
        result.subscribe((res: HttpResponse<UserDeviceWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserDeviceWebae) {
        this.eventManager.broadcast({ name: 'userDeviceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserPreferenceById(index: number, item: UserPreferenceWebae) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-device-webae-popup',
    template: ''
})
export class UserDeviceWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDevicePopupService: UserDeviceWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userDevicePopupService
                    .open(UserDeviceWebaeDialogComponent as Component, params['id']);
            } else {
                this.userDevicePopupService
                    .open(UserDeviceWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
