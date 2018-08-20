/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { UserPreferenceWebaeComponent } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae.component';
import { UserPreferenceWebaeService } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae.service';
import { UserPreferenceWebae } from '../../../../../../main/webapp/app/entities/user-and-preference-webae/user-and-preference-webae.model';

describe('Component Tests', () => {

    describe('UserPreferenceWebae Management Component', () => {
        let comp: UserPreferenceWebaeComponent;
        let fixture: ComponentFixture<UserPreferenceWebaeComponent>;
        let service: UserPreferenceWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserPreferenceWebaeComponent],
                providers: [
                    UserPreferenceWebaeService
                ]
            })
            .overrideTemplate(UserPreferenceWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserPreferenceWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserPreferenceWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserPreferenceWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userPreferences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
