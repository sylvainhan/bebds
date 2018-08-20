/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { DemandeWebaeComponent } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.component';
import { DemandeWebaeService } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.service';
import { DemandeWebae } from '../../../../../../main/webapp/app/entities/demande-webae/demande-webae.model';

describe('Component Tests', () => {

    describe('DemandeWebae Management Component', () => {
        let comp: DemandeWebaeComponent;
        let fixture: ComponentFixture<DemandeWebaeComponent>;
        let service: DemandeWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [DemandeWebaeComponent],
                providers: [
                    DemandeWebaeService
                ]
            })
            .overrideTemplate(DemandeWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DemandeWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.demandes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
