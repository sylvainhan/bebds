import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LastConnectionWebae } from './last-connection-webae.model';
import { LastConnectionWebaeService } from './last-connection-webae.service';

@Component({
    selector: 'jhi-last-connection-webae-detail',
    templateUrl: './last-connection-webae-detail.component.html'
})
export class LastConnectionWebaeDetailComponent implements OnInit, OnDestroy {

    lastConnection: LastConnectionWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lastConnectionService: LastConnectionWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLastConnections();
    }

    load(id) {
        this.lastConnectionService.find(id)
            .subscribe((lastConnectionResponse: HttpResponse<LastConnectionWebae>) => {
                this.lastConnection = lastConnectionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLastConnections() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lastConnectionListModification',
            (response) => this.load(this.lastConnection.id)
        );
    }
}
