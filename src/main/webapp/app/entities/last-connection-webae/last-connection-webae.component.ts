import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LastConnectionWebae } from './last-connection-webae.model';
import { LastConnectionWebaeService } from './last-connection-webae.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-last-connection-webae',
    templateUrl: './last-connection-webae.component.html'
})
export class LastConnectionWebaeComponent implements OnInit, OnDestroy {
lastConnections: LastConnectionWebae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private lastConnectionService: LastConnectionWebaeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.lastConnectionService.query().subscribe(
            (res: HttpResponse<LastConnectionWebae[]>) => {
                this.lastConnections = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLastConnections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LastConnectionWebae) {
        return item.id;
    }
    registerChangeInLastConnections() {
        this.eventSubscriber = this.eventManager.subscribe('lastConnectionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
