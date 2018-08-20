/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { TauxDetailComponent } from '../../../../../../main/webapp/app/entities/taux/taux-detail.component';
import { TauxService } from '../../../../../../main/webapp/app/entities/taux/taux.service';
import { Taux } from '../../../../../../main/webapp/app/entities/taux/taux.model';

describe('Component Tests', () => {

    describe('Taux Management Detail Component', () => {
        let comp: TauxDetailComponent;
        let fixture: ComponentFixture<TauxDetailComponent>;
        let service: TauxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [TauxDetailComponent],
                providers: [
                    TauxService
                ]
            })
            .overrideTemplate(TauxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TauxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TauxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Taux(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.taux).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
