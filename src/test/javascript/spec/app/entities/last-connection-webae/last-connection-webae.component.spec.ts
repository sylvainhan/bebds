/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { LastConnectionWebaeComponent } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.component';
import { LastConnectionWebaeService } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.service';
import { LastConnectionWebae } from '../../../../../../main/webapp/app/entities/last-connection-webae/last-connection-webae.model';

describe('Component Tests', () => {

    describe('LastConnectionWebae Management Component', () => {
        let comp: LastConnectionWebaeComponent;
        let fixture: ComponentFixture<LastConnectionWebaeComponent>;
        let service: LastConnectionWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [LastConnectionWebaeComponent],
                providers: [
                    LastConnectionWebaeService
                ]
            })
            .overrideTemplate(LastConnectionWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LastConnectionWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LastConnectionWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LastConnectionWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lastConnections[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
