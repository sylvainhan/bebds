/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { UserPreferenceWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae-detail.component';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae.service';
import { UserPreferenceWebae } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae.model';

describe('Component Tests', () => {

    describe('UserPreferenceWebae Management Detail Component', () => {
        let comp: UserPreferenceWebaeDetailComponent;
        let fixture: ComponentFixture<UserPreferenceWebaeDetailComponent>;
        let service: UserPreferenceWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserPreferenceWebaeDetailComponent],
                providers: [
                    UserPreferenceWebaeService
                ]
            })
            .overrideTemplate(UserPreferenceWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPreferenceWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPreferenceWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserPreferenceWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userPreference).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
