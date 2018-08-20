import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NotificationWebae } from './notification-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NotificationWebae>;

@Injectable()
export class NotificationWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/notifications';

    constructor(private http: HttpClient) { }

    create(notification: NotificationWebae): Observable<EntityResponseType> {
        const copy = this.convert(notification);
        return this.http.post<NotificationWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(notification: NotificationWebae): Observable<EntityResponseType> {
        const copy = this.convert(notification);
        return this.http.put<NotificationWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NotificationWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NotificationWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<NotificationWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NotificationWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NotificationWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NotificationWebae[]>): HttpResponse<NotificationWebae[]> {
        const jsonResponse: NotificationWebae[] = res.body;
        const body: NotificationWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NotificationWebae.
     */
    private convertItemFromServer(notification: NotificationWebae): NotificationWebae {
        const copy: NotificationWebae = Object.assign({}, notification);
        return copy;
    }

    /**
     * Convert a NotificationWebae to a JSON which can be sent to the server.
     */
    private convert(notification: NotificationWebae): NotificationWebae {
        const copy: NotificationWebae = Object.assign({}, notification);
        return copy;
    }
}
