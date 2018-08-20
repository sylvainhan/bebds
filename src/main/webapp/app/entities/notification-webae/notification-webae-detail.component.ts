import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NotificationWebae } from './notification-webae.model';
import { NotificationWebaeService } from './notification-webae.service';

@Component({
    selector: 'jhi-notification-webae-detail',
    templateUrl: './notification-webae-detail.component.html'
})
export class NotificationWebaeDetailComponent implements OnInit, OnDestroy {

    notification: NotificationWebae;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private notificationService: NotificationWebaeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNotifications();
    }

    load(id) {
        this.notificationService.find(id)
            .subscribe((notificationResponse: HttpResponse<NotificationWebae>) => {
                this.notification = notificationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNotifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'notificationListModification',
            (response) => this.load(this.notification.id)
        );
    }
}
