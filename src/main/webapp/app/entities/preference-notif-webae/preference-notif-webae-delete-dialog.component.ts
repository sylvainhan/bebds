import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { PreferenceNotifWebaePopupService } from './preference-notif-webae-popup.service';
import { PreferenceNotifWebaeService } from './preference-notif-webae.service';

@Component({
    selector: 'jhi-preference-notif-webae-delete-dialog',
    templateUrl: './preference-notif-webae-delete-dialog.component.html'
})
export class PreferenceNotifWebaeDeleteDialogComponent {

    preferenceNotif: PreferenceNotifWebae;

    constructor(
        private preferenceNotifService: PreferenceNotifWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.preferenceNotifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'preferenceNotifListModification',
                content: 'Deleted an preferenceNotif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-preference-notif-webae-delete-popup',
    template: ''
})
export class PreferenceNotifWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private preferenceNotifPopupService: PreferenceNotifWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.preferenceNotifPopupService
                .open(PreferenceNotifWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
