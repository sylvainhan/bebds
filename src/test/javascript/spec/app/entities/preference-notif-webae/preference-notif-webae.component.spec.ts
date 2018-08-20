/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { PreferenceNotifWebaeComponent } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.component';
import { PreferenceNotifWebaeService } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.service';
import { PreferenceNotifWebae } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.model';

describe('Component Tests', () => {

    describe('PreferenceNotifWebae Management Component', () => {
        let comp: PreferenceNotifWebaeComponent;
        let fixture: ComponentFixture<PreferenceNotifWebaeComponent>;
        let service: PreferenceNotifWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [PreferenceNotifWebaeComponent],
                providers: [
                    PreferenceNotifWebaeService
                ]
            })
            .overrideTemplate(PreferenceNotifWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreferenceNotifWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreferenceNotifWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PreferenceNotifWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.preferenceNotifs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
