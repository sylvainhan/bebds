/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { UserDeviceWebaeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae-delete-dialog.component';
import { UserDeviceWebaeService } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.service';

describe('Component Tests', () => {

    describe('UserDeviceWebae Management Delete Component', () => {
        let comp: UserDeviceWebaeDeleteDialogComponent;
        let fixture: ComponentFixture<UserDeviceWebaeDeleteDialogComponent>;
        let service: UserDeviceWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserDeviceWebaeDeleteDialogComponent],
                providers: [
                    UserDeviceWebaeService
                ]
            })
            .overrideTemplate(UserDeviceWebaeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDeviceWebaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDeviceWebaeService);
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
