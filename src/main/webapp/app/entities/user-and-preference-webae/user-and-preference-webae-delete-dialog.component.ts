import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserPreferenceWebae } from './user-and-preference-webae.model';
import { UserPreferenceWebaePopupService } from './user-and-preference-webae-popup.service';
import { UserPreferenceWebaeService } from './user-and-preference-webae.service';

@Component({
    selector: 'jhi-user-and-preference-webae-delete-dialog',
    templateUrl: './user-and-preference-webae-delete-dialog.component.html'
})
export class UserPreferenceWebaeDeleteDialogComponent {

    userPreference: UserPreferenceWebae;

    constructor(
        private userPreferenceService: UserPreferenceWebaeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPreferenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userPreferenceListModification',
                content: 'Deleted an userPreference'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-and-preference-webae-delete-popup',
    template: ''
})
export class UserPreferenceWebaeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPreferencePopupService: UserPreferenceWebaePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userPreferencePopupService
                .open(UserPreferenceWebaeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
