import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { UserDeviceWebae } from './user-device-webae.model';
import { UserDeviceWebaeService } from './user-device-webae.service';

@Injectable()
export class UserDeviceWebaePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userDeviceService: UserDeviceWebaeService

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
                this.userDeviceService.find(id)
                    .subscribe((userDeviceResponse: HttpResponse<UserDeviceWebae>) => {
                        const userDevice: UserDeviceWebae = userDeviceResponse.body;
                        this.ngbModalRef = this.userDeviceModalRef(component, userDevice);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userDeviceModalRef(component, new UserDeviceWebae());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userDeviceModalRef(component: Component, userDevice: UserDeviceWebae): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userDevice = userDevice;
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
