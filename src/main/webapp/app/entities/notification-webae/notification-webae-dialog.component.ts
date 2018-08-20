import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NotificationWebae } from './notification-webae.model';
import { NotificationWebaePopupService } from './notification-webae-popup.service';
import { NotificationWebaeService } from './notification-webae.service';
import { UserPreferenceWebae, UserPreferenceWebaeService } from '../user-and-preference-webae';

@Component({
    selector: 'jhi-notification-webae-dialog',
    templateUrl: './notification-webae-dialog.component.html'
})
export class NotificationWebaeDialogComponent implements OnInit {

    notification: NotificationWebae;
    isSaving: boolean;

    userandpreferences: UserPreferenceWebae[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private notificationService: NotificationWebaeService,
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
        if (this.notification.id !== undefined) {
            this.subscribeToSaveResponse(
                this.notificationService.update(this.notification));
        } else {
            this.subscribeToSaveResponse(
                this.notificationService.create(this.notification));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NotificationWebae>>) {
        result.subscribe((res: HttpResponse<NotificationWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NotificationWebae) {
        this.eventManager.broadcast({ name: 'notificationListModification', content: 'OK'});
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
    selector: 'jhi-notification-webae-popup',
    template: ''
})
export class NotificationWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notificationPopupService: NotificationWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.notificationPopupService
                    .open(NotificationWebaeDialogComponent as Component, params['id']);
            } else {
                this.notificationPopupService
                    .open(NotificationWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
