import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DemandeWebae } from './demande-webae.model';
import { DemandeWebaeService } from './demande-webae.service';

@Component({
    selector: 'jhi-demande-webae-detail',
    templateUrl: './demande-webae-detail.component.html'
})
export class DemandeWebaeDetailComponent implements OnInit, OnDestroy {

    demande: DemandeWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private demandeService: DemandeWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDemandes();
    }

    load(id) {
        this.demandeService.find(id)
            .subscribe((demandeResponse: HttpResponse<DemandeWebae>) => {
                this.demande = demandeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDemandes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'demandeListModification',
            (response) => this.load(this.demande.id)
        );
    }
}
