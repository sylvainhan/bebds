/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { PreferenceNotifWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae-detail.component';
import { PreferenceNotifWebaeService } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.service';
import { PreferenceNotifWebae } from '../../../../../../main/webapp/app/entities/preference-notif-webae/preference-notif-webae.model';

describe('Component Tests', () => {

    describe('PreferenceNotifWebae Management Detail Component', () => {
        let comp: PreferenceNotifWebaeDetailComponent;
        let fixture: ComponentFixture<PreferenceNotifWebaeDetailComponent>;
        let service: PreferenceNotifWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [PreferenceNotifWebaeDetailComponent],
                providers: [
                    PreferenceNotifWebaeService
                ]
            })
            .overrideTemplate(PreferenceNotifWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreferenceNotifWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreferenceNotifWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PreferenceNotifWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.preferenceNotif).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
