/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { PreferenceNotifWebaeDialogComponent } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae-dialog.component';
import { PreferenceNotifWebaeService } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.service';
import { PreferenceNotifWebae } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.model';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae';

describe('Component Tests', () => {

    describe('PreferenceNotifWebae Management Dialog Component', () => {
        let comp: PreferenceNotifWebaeDialogComponent;
        let fixture: ComponentFixture<PreferenceNotifWebaeDialogComponent>;
        let service: PreferenceNotifWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [PreferenceNotifWebaeDialogComponent],
                providers: [
                    UserPreferenceWebaeService,
                    PreferenceNotifWebaeService
                ]
            })
            .overrideTemplate(PreferenceNotifWebaeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreferenceNotifWebaeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreferenceNotifWebaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PreferenceNotifWebae(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.preferenceNotif = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'preferenceNotifListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PreferenceNotifWebae();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.preferenceNotif = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'preferenceNotifListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
