/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { TauxComponent } from '../../../../../../main/webapp/app/entities/taux/taux.component';
import { TauxService } from '../../../../../../main/webapp/app/entities/taux/taux.service';
import { Taux } from '../../../../../../main/webapp/app/entities/taux/taux.model';

describe('Component Tests', () => {

    describe('Taux Management Component', () => {
        let comp: TauxComponent;
        let fixture: ComponentFixture<TauxComponent>;
        let service: TauxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [TauxComponent],
                providers: [
                    TauxService
                ]
            })
            .overrideTemplate(TauxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TauxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TauxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Taux(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tauxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
