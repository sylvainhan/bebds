import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LastConnectionWebae } from './last-connection-webae.model';
import { LastConnectionWebaePopupService } from './last-connection-webae-popup.service';
import { LastConnectionWebaeService } from './last-connection-webae.service';

@Component({
    selector: 'jhi-last-connection-webae-delete-dialog',
    templateUrl: './last-connection-webae-delete-dialog.component.html'
})
export class LastConnectionWebaeDeleteDialogComponent {

    lastConnection: LastConnectionWebae;

    constructor(
        private lastConnectionService: LastConnectionWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lastConnectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lastConnectionListModification',
                content: 'Deleted an lastConnection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-last-connection-webae-delete-popup',
    template: ''
})
export class LastConnectionWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lastConnectionPopupService: LastConnectionWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lastConnectionPopupService
                .open(LastConnectionWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
