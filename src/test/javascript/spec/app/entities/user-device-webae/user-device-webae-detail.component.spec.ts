/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { UserDeviceWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae-detail.component';
import { UserDeviceWebaeService } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.service';
import { UserDeviceWebae } from '../../../../../../main/webapp/app/entities/user-device-webae/user-device-webae.model';

describe('Component Tests', () => {

    describe('UserDeviceWebae Management Detail Component', () => {
        let comp: UserDeviceWebaeDetailComponent;
        let fixture: ComponentFixture<UserDeviceWebaeDetailComponent>;
        let service: UserDeviceWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [UserDeviceWebaeDetailComponent],
                providers: [
                    UserDeviceWebaeService
                ]
            })
            .overrideTemplate(UserDeviceWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDeviceWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDeviceWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new UserDeviceWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userDevice).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
