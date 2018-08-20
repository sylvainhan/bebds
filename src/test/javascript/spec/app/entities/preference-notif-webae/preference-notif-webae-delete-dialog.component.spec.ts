/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { PreferenceNotifWebaeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae-delete-dialog.component';
import { PreferenceNotifWebaeService } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.service';

describe('Component Tests', () => {

    describe('PreferenceNotifWebae Management Delete Component', () => {
        let comp: PreferenceNotifWebaeDeleteDialogComponent;
        let fixture: ComponentFixture<PreferenceNotifWebaeDeleteDialogComponent>;
        let service: PreferenceNotifWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [PreferenceNotifWebaeDeleteDialogComponent],
                providers: [
                    PreferenceNotifWebaeService
                ]
            })
            .overrideTemplate(PreferenceNotifWebaeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreferenceNotifWebaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreferenceNotifWebaeService);
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
