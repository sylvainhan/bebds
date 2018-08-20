import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Taux } from './taux.model';
import { TauxPopupService } from './taux-popup.service';
import { TauxService } from './taux.service';

@Component({
    selector: 'jhi-taux-dialog',
    templateUrl: './taux-dialog.component.html'
})
export class TauxDialogComponent implements OnInit {

    taux: Taux;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tauxService: TauxService,
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
        if (this.taux.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tauxService.update(this.taux));
        } else {
            this.subscribeToSaveResponse(
                this.tauxService.create(this.taux));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Taux>>) {
        result.subscribe((res: HttpResponse<Taux>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Taux) {
        this.eventManager.broadcast({ name: 'tauxListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-taux-popup',
    template: ''
})
export class TauxPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tauxPopupService: TauxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tauxPopupService
                    .open(TauxDialogComponent as Component, params['id']);
            } else {
                this.tauxPopupService
                    .open(TauxDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
