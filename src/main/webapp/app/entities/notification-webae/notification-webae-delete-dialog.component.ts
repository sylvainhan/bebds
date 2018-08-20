import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NotificationWebae } from './notification-webae.model';
import { NotificationWebaePopupService } from './notification-webae-popup.service';
import { NotificationWebaeService } from './notification-webae.service';

@Component({
    selector: 'jhi-notification-webae-delete-dialog',
    templateUrl: './notification-webae-delete-dialog.component.html'
})
export class NotificationWebaeDeleteDialogComponent {

    notification: NotificationWebae;

    constructor(
        private notificationService: NotificationWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.notificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'notificationListModification',
                content: 'Deleted an notification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-notification-webae-delete-popup',
    template: ''
})
export class NotificationWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notificationPopupService: NotificationWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.notificationPopupService
                .open(NotificationWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
