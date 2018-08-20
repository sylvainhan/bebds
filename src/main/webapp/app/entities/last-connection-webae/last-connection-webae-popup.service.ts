import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { LastConnectionWebae } from './last-connection-webae.model';
import { LastConnectionWebaeService } from './last-connection-webae.service';

@Injectable()
export class LastConnectionWebaePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private lastConnectionService: LastConnectionWebaeService

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
                this.lastConnectionService.find(id)
                    .subscribe((lastConnectionResponse: HttpResponse<LastConnectionWebae>) => {
                        const lastConnection: LastConnectionWebae = lastConnectionResponse.body;
                        lastConnection.actuelConnection = this.datePipe
                            .transform(lastConnection.actuelConnection, 'yyyy-MM-ddTHH:mm:ss');
                        lastConnection.lastConnection = this.datePipe
                            .transform(lastConnection.lastConnection, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.lastConnectionModalRef(component, lastConnection);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.lastConnectionModalRef(component, new LastConnectionWebae());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    lastConnectionModalRef(component: Component, lastConnection: LastConnectionWebae): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lastConnection = lastConnection;
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
