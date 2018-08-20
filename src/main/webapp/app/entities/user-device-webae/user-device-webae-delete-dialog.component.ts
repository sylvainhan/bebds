import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserDeviceWebae } from './user-device-webae.model';
import { UserDeviceWebaePopupService } from './user-device-webae-popup.service';
import { UserDeviceWebaeService } from './user-device-webae.service';

@Component({
    selector: 'jhi-user-device-webae-delete-dialog',
    templateUrl: './user-device-webae-delete-dialog.component.html'
})
export class UserDeviceWebaeDeleteDialogComponent {

    userDevice: UserDeviceWebae;

    constructor(
        private userDeviceService: UserDeviceWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userDeviceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userDeviceListModification',
                content: 'Deleted an userDevice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-device-webae-delete-popup',
    template: ''
})
export class UserDeviceWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDevicePopupService: UserDeviceWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userDevicePopupService
                .open(UserDeviceWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
