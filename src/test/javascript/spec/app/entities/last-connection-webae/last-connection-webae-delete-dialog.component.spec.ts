/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { WebaeTestModule } from '../../../test.module';
import { LastConnectionWebaeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae-delete-dialog.component';
import { LastConnectionWebaeService } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.service';

describe('Component Tests', () => {

    describe('LastConnectionWebae Management Delete Component', () => {
        let comp: LastConnectionWebaeDeleteDialogComponent;
        let fixture: ComponentFixture<LastConnectionWebaeDeleteDialogComponent>;
        let service: LastConnectionWebaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [LastConnectionWebaeDeleteDialogComponent],
                providers: [
                    LastConnectionWebaeService
                ]
            })
            .overrideTemplate(LastConnectionWebaeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LastConnectionWebaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LastConnectionWebaeService);
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
