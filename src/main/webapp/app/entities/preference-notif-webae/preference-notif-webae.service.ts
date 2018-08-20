import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PreferenceNotifWebae } from './preference-notif-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PreferenceNotifWebae>;

@Injectable()
export class PreferenceNotifWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/preference-notifs';

    constructor(private http: HttpClient) { }

    create(preferenceNotif: PreferenceNotifWebae): Observable<EntityResponseType> {
        const copy = this.convert(preferenceNotif);
        return this.http.post<PreferenceNotifWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(preferenceNotif: PreferenceNotifWebae): Observable<EntityResponseType> {
        const copy = this.convert(preferenceNotif);
        return this.http.put<PreferenceNotifWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PreferenceNotifWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PreferenceNotifWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<PreferenceNotifWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PreferenceNotifWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PreferenceNotifWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PreferenceNotifWebae[]>): HttpResponse<PreferenceNotifWebae[]> {
        const jsonResponse: PreferenceNotifWebae[] = res.body;
        const body: PreferenceNotifWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PreferenceNotifWebae.
     */
    private convertItemFromServer(preferenceNotif: PreferenceNotifWebae): PreferenceNotifWebae {
        const copy: PreferenceNotifWebae = Object.assign({}, preferenceNotif);
        return copy;
    }

    /**
     * Convert a PreferenceNotifWebae to a JSON which can be sent to the server.
     */
    private convert(preferenceNotif: PreferenceNotifWebae): PreferenceNotifWebae {
        const copy: PreferenceNotifWebae = Object.assign({}, preferenceNotif);
        return copy;
    }
}
