import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NotificationWebae } from './notification-webae.model';
import { NotificationWebaeService } from './notification-webae.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-notification-webae',
    templateUrl: './notification-webae.component.html'
})
export class NotificationWebaeComponent implements OnInit, OnDestroy {
notifications: NotificationWebae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private notificationService: NotificationWebaeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.notificationService.query().subscribe(
            (res: HttpResponse<NotificationWebae[]>) => {
                this.notifications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInNotifications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: NotificationWebae) {
        return item.id;
    }
    registerChangeInNotifications() {
        this.eventSubscriber = this.eventManager.subscribe('notificationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
