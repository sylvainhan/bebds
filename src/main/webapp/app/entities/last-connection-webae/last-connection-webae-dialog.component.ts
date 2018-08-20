import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LastConnectionWebae } from './last-connection-webae.model';
import { LastConnectionWebaePopupService } from './last-connection-webae-popup.service';
import { LastConnectionWebaeService } from './last-connection-webae.service';
import { UserPreferenceWebae, UserPreferenceWebaeService } from '../user-and-preference-webae';

@Component({
    selector: 'jhi-last-connection-webae-dialog',
    templateUrl: './last-connection-webae-dialog.component.html'
})
export class LastConnectionWebaeDialogComponent implements OnInit {

    lastConnection: LastConnectionWebae;
    isSaving: boolean;

    userandpreferences: UserPreferenceWebae[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lastConnectionService: LastConnectionWebaeService,
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
        if (this.lastConnection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lastConnectionService.update(this.lastConnection));
        } else {
            this.subscribeToSaveResponse(
                this.lastConnectionService.create(this.lastConnection));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LastConnectionWebae>>) {
        result.subscribe((res: HttpResponse<LastConnectionWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LastConnectionWebae) {
        this.eventManager.broadcast({ name: 'lastConnectionListModification', content: 'OK'});
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
    selector: 'jhi-last-connection-webae-popup',
    template: ''
})
export class LastConnectionWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lastConnectionPopupService: LastConnectionWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lastConnectionPopupService
                    .open(LastConnectionWebaeDialogComponent as Component, params['id']);
            } else {
                this.lastConnectionPopupService
                    .open(LastConnectionWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
