/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { LastConnectionWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae-detail.component';
import { LastConnectionWebaeService } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.service';
import { LastConnectionWebae } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.model';

describe('Component Tests', () => {

    describe('LastConnectionWebae Management Detail Component', () => {
        let comp: LastConnectionWebaeDetailComponent;
        let fixture: ComponentFixture<LastConnectionWebaeDetailComponent>;
        let service: LastConnectionWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [LastConnectionWebaeDetailComponent],
                providers: [
                    LastConnectionWebaeService
                ]
            })
            .overrideTemplate(LastConnectionWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LastConnectionWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LastConnectionWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LastConnectionWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lastConnection).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
