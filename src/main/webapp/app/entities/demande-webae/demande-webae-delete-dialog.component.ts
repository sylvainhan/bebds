import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DemandeWebae } from './demande-webae.model';
import { DemandeWebaePopupService } from './demande-webae-popup.service';
import { DemandeWebaeService } from './demande-webae.service';

@Component({
    selector: 'jhi-demande-webae-delete-dialog',
    templateUrl: './demande-webae-delete-dialog.component.html'
})
export class DemandeWebaeDeleteDialogComponent {

    demande: DemandeWebae;

    constructor(
        private demandeService: DemandeWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.demandeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'demandeListModification',
                content: 'Deleted an demande'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demande-webae-delete-popup',
    template: ''
})
export class DemandeWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private demandePopupService: DemandeWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.demandePopupService
                .open(DemandeWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
