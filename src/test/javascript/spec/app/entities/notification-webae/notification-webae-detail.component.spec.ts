/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WebaeTestModule } from '../../../test.module';
import { NotificationWebaeDetailComponent } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae-detail.component';
import { NotificationWebaeService } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.service';
import { NotificationWebae } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.model';

describe('Component Tests', () => {

    describe('NotificationWebae Management Detail Component', () => {
        let comp: NotificationWebaeDetailComponent;
        let fixture: ComponentFixture<NotificationWebaeDetailComponent>;
        let service: NotificationWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [NotificationWebaeDetailComponent],
                providers: [
                    NotificationWebaeService
                ]
            })
            .overrideTemplate(NotificationWebaeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationWebaeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NotificationWebae(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.notification).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
