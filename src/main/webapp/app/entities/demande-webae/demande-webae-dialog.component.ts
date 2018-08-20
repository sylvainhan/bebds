import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DemandeWebae } from './demande-webae.model';
import { DemandeWebaePopupService } from './demande-webae-popup.service';
import { DemandeWebaeService } from './demande-webae.service';
import { UserPreferenceWebae, UserPreferenceWebaeService } from '../user-and-preference-webae';

@Component({
    selector: 'jhi-demande-webae-dialog',
    templateUrl: './demande-webae-dialog.component.html'
})
export class DemandeWebaeDialogComponent implements OnInit {

    demande: DemandeWebae;
    isSaving: boolean;

    userandpreferences: UserPreferenceWebae[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private demandeService: DemandeWebaeService,
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
        if (this.demande.id !== undefined) {
            this.subscribeToSaveResponse(
                this.demandeService.update(this.demande));
        } else {
            this.subscribeToSaveResponse(
                this.demandeService.create(this.demande));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DemandeWebae>>) {
        result.subscribe((res: HttpResponse<DemandeWebae>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DemandeWebae) {
        this.eventManager.broadcast({ name: 'demandeListModification', content: 'OK'});
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
    selector: 'jhi-demande-webae-popup',
    template: ''
})
export class DemandeWebaePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private demandePopupService: DemandeWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.demandePopupService
                    .open(DemandeWebaeDialogComponent as Component, params['id']);
            } else {
                this.demandePopupService
                    .open(DemandeWebaeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
