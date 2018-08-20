/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { DemandeWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae-detail.component';
import { DemandeWebaeService } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.service';
import { DemandeWebae } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.model';

describe('Component Tests', () => {

    describe('DemandeWebae Management Detail Component', () => {
        let comp: DemandeWebaeDetailComponent;
        let fixture: ComponentFixture<DemandeWebaeDetailComponent>;
        let service: DemandeWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [DemandeWebaeDetailComponent],
                providers: [
                    DemandeWebaeService
                ]
            })
            .overrideTemplate(DemandeWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DemandeWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.demande).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
