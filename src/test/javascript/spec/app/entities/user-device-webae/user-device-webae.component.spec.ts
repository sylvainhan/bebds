/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { UserDeviceWebaeComponent } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.component';
import { UserDeviceWebaeService } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.service';
import { UserDeviceWebae } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.model';

describe('Component Tests', () => {

    describe('UserDeviceWebae Management Component', () => {
        let comp: UserDeviceWebaeComponent;
        let fixture: ComponentFixture<UserDeviceWebaeComponent>;
        let service: UserDeviceWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserDeviceWebaeComponent],
                providers: [
                    UserDeviceWebaeService
                ]
            })
            .overrideTemplate(UserDeviceWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDeviceWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDeviceWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserDeviceWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userDevices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
