import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { PreferenceNotifWebaeService } from './preference-notif-webae.service';

@Injectable()
export class PreferenceNotifWebaePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private preferenceNotifService: PreferenceNotifWebaeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.preferenceNotifService.find(id)
                    .subscribe((preferenceNotifResponse: HttpResponse<PreferenceNotifWebae>) => {
                        const preferenceNotif: PreferenceNotifWebae = preferenceNotifResponse.body;
                        this.ngbModalRef = this.preferenceNotifModalRef(component, preferenceNotif);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.preferenceNotifModalRef(component, new PreferenceNotifWebae());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    preferenceNotifModalRef(component: Component, preferenceNotif: PreferenceNotifWebae): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.preferenceNotif = preferenceNotif;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
