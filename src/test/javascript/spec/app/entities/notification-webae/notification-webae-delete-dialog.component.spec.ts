/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { NotificationWebaeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae-delete-dialog.component';
import { NotificationWebaeService } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.service';

describe('Component Tests', () => {

    describe('NotificationWebae Management Delete Component', () => {
        let comp: NotificationWebaeDeleteDialogComponent;
        let fixture: ComponentFixture<NotificationWebaeDeleteDialogComponent>;
        let service: NotificationWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [NotificationWebaeDeleteDialogComponent],
                providers: [
                    NotificationWebaeService
                ]
            })
            .overrideTemplate(NotificationWebaeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationWebaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationWebaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
