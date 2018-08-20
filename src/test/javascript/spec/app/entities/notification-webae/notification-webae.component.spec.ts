/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WebaeTestModule } from '../../../test.module';
import { NotificationWebaeComponent } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.component';
import { NotificationWebaeService } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.service';
import { NotificationWebae } from '../../../../../../main/webapp/app/entities/notification-webae/notification-webae.model';

describe('Component Tests', () => {

    describe('NotificationWebae Management Component', () => {
        let comp: NotificationWebaeComponent;
        let fixture: ComponentFixture<NotificationWebaeComponent>;
        let service: NotificationWebaeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebaeTestModule],
                declarations: [NotificationWebaeComponent],
                providers: [
                    NotificationWebaeService
                ]
            })
            .overrideTemplate(NotificationWebaeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NotificationWebaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NotificationWebaeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NotificationWebae(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.notifications[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
