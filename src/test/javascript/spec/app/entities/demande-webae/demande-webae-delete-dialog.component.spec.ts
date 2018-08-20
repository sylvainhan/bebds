/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { DemandeWebaeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae-delete-dialog.component';
import { DemandeWebaeService } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.service';

describe('Component Tests', () => {

    describe('DemandeWebae Management Delete Component', () => {
        let comp: DemandeWebaeDeleteDialogComponent;
        let fixture: ComponentFixture<DemandeWebaeDeleteDialogComponent>;
        let service: DemandeWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [DemandeWebaeDeleteDialogComponent],
                providers: [
                    DemandeWebaeService
                ]
            })
            .overrideTemplate(DemandeWebaeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeWebaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeWebaeService);
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
