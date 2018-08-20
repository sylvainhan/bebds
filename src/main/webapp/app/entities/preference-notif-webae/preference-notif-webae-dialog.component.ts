import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { PreferenceNotifWebaePopupService } from './preference-notif-webae-popup.service';
import { PreferenceNotifWebaeService } from './preference-notif-webae.service';
import { UserPreferenceWebae, UserPreferenceWebaeService } from '../user-and-preference-webae';

@Component({
    selector: 'jhi-preference-notif-webae-dialog',
    templateUrl: './preference-notif-webae-dialog.component.html'
})
export class PreferenceNotifWebaeDialogComponent implements OnInit {

    preferenceNotif: PreferenceNotifWebae;
    isSaving: boolean;

    userandpreferences: UserPreferenceWebae[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private preferenceNotifService: PreferenceNotifWebaeService,
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
        if (this.preferenceNotif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.preferenceNotifService.update(this.preferenceNotif));
        } else {
            this.subscribeToSaveResponse(
                this.preferenceNotifService.create(this.preferenceNotif));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PreferenceNotifWebae>>) {
        result.subscribe((res: HttpResponse<PreferenceNotifWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PreferenceNotifWebae) {
        this.eventManager.broadcast({ name: 'preferenceNotifListModification', content: 'OK'});
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
    selector: 'jhi-preference-notif-webae-popup',
    template: ''
})
export class PreferenceNotifWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private preferenceNotifPopupService: PreferenceNotifWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.preferenceNotifPopupService
                    .open(PreferenceNotifWebaeDialogComponent as Component, params['id']);
            } else {
                this.preferenceNotifPopupService
                    .open(PreferenceNotifWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
