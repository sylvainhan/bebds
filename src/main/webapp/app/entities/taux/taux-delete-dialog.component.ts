import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Taux } from './taux.model';
import { TauxPopupService } from './taux-popup.service';
import { TauxService } from './taux.service';

@Component({
    selector: 'jhi-taux-delete-dialog',
    templateUrl: './taux-delete-dialog.component.html'
})
export class TauxDeleteDialogComponent {

    taux: Taux;

    constructor(
        private tauxService: TauxService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tauxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tauxListModification',
                content: 'Deleted an taux'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-taux-delete-popup',
    template: ''
})
export class TauxDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tauxPopupService: TauxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tauxPopupService
                .open(TauxDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
