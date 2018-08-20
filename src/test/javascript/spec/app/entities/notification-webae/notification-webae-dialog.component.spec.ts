/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { NotificationWebaeDialogComponent } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae-dialog.component';
import { NotificationWebaeService } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.service';
import { NotificationWebae } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.model';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae';

describe('Component Tests', () => {

    describe('NotificationWebae Management Dialog Component', () => {
        let comp: NotificationWebaeDialogComponent;
        let fixture: ComponentFixture<NotificationWebaeDialogComponent>;
        let service: NotificationWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [NotificationWebaeDialogComponent],
                providers: [
                    UserPreferenceWebaeService,
                    NotificationWebaeService
                ]
            })
            .overrideTemplate(NotificationWebaeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationWebaeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationWebaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NotificationWebae(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notification = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notificationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NotificationWebae();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.notification = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'notificationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
