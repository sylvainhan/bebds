/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { UserDeviceWebaeDialogComponent } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae-dialog.component';
import { UserDeviceWebaeService } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.service';
import { UserDeviceWebae } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.model';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae';

describe('Component Tests', () => {

    describe('UserDeviceWebae Management Dialog Component', () => {
        let comp: UserDeviceWebaeDialogComponent;
        let fixture: ComponentFixture<UserDeviceWebaeDialogComponent>;
        let service: UserDeviceWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserDeviceWebaeDialogComponent],
                providers: [
                    UserPreferenceWebaeService,
                    UserDeviceWebaeService
                ]
            })
            .overrideTemplate(UserDeviceWebaeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDeviceWebaeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDeviceWebaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserDeviceWebae(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userDevice = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userDeviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserDeviceWebae();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userDevice = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userDeviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
