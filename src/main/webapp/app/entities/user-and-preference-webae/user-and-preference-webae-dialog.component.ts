import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserPreferenceWebae } from './user-and-preference-webae.model';
import { UserPreferenceWebaePopupService } from './user-and-preference-webae-popup.service';
import { UserPreferenceWebaeService } from './user-and-preference-webae.service';

@Component({
    selector: 'jhi-user-and-preference-webae-dialog',
    templateUrl: './user-and-preference-webae-dialog.component.html'
})
export class UserPreferenceWebaeDialogComponent implements OnInit {

    userPreference: UserPreferenceWebae;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private userPreferenceService: UserPreferenceWebaeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userPreference.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userPreferenceService.update(this.userPreference));
        } else {
            this.subscribeToSaveResponse(
                this.userPreferenceService.create(this.userPreference));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserPreferenceWebae>>) {
        result.subscribe((res: HttpResponse<UserPreferenceWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserPreferenceWebae) {
        this.eventManager.broadcast({ name: 'userPreferenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-user-and-preference-webae-popup',
    template: ''
})
export class UserPreferenceWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPreferencePopupService: UserPreferenceWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userPreferencePopupService
                    .open(UserPreferenceWebaeDialogComponent as Component, params['id']);
            } else {
                this.userPreferencePopupService
                    .open(UserPreferenceWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
