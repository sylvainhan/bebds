/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { DemandeWebaeDialogComponent } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae-dialog.component';
import { DemandeWebaeService } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.service';
import { DemandeWebae } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.model';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae';

describe('Component Tests', () => {

    describe('DemandeWebae Management Dialog Component', () => {
        let comp: DemandeWebaeDialogComponent;
        let fixture: ComponentFixture<DemandeWebaeDialogComponent>;
        let service: DemandeWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [DemandeWebaeDialogComponent],
                providers: [
                    UserPreferenceWebaeService,
                    DemandeWebaeService
                ]
            })
            .overrideTemplate(DemandeWebaeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeWebaeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeWebaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DemandeWebae(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.demande = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'demandeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DemandeWebae();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.demande = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'demandeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
