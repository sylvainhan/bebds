import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Taux } from './taux.model';
import { TauxService } from './taux.service';

@Component({
    selector: 'jhi-taux-detail',
    templateUrl: './taux-detail.component.html'
})
export class TauxDetailComponent implements OnInit, OnDestroy {

    taux: Taux;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tauxService: TauxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTauxes();
    }

    load(id) {
        this.tauxService.find(id)
            .subscribe((tauxResponse: HttpResponse<Taux>) => {
                this.taux = tauxResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTauxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tauxListModification',
            (response) => this.load(this.taux.id)
        );
    }
}
