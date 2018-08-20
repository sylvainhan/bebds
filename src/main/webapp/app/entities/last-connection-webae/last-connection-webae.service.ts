import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LastConnectionWebae } from './last-connection-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LastConnectionWebae>;

@Injectable()
export class LastConnectionWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/last-connections';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(lastConnection: LastConnectionWebae): Observable<EntityResponseType> {
        const copy = this.convert(lastConnection);
        return this.http.post<LastConnectionWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(lastConnection: LastConnectionWebae): Observable<EntityResponseType> {
        const copy = this.convert(lastConnection);
        return this.http.put<LastConnectionWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LastConnectionWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LastConnectionWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<LastConnectionWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LastConnectionWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LastConnectionWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LastConnectionWebae[]>): HttpResponse<LastConnectionWebae[]> {
        const jsonResponse: LastConnectionWebae[] = res.body;
        const body: LastConnectionWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LastConnectionWebae.
     */
    private convertItemFromServer(lastConnection: LastConnectionWebae): LastConnectionWebae {
        const copy: LastConnectionWebae = Object.assign({}, lastConnection);
        copy.actuelConnection = this.dateUtils
            .convertDateTimeFromServer(lastConnection.actuelConnection);
        copy.lastConnection = this.dateUtils
            .convertDateTimeFromServer(lastConnection.lastConnection);
        return copy;
    }

    /**
     * Convert a LastConnectionWebae to a JSON which can be sent to the server.
     */
    private convert(lastConnection: LastConnectionWebae): LastConnectionWebae {
        const copy: LastConnectionWebae = Object.assign({}, lastConnection);

        copy.actuelConnection = this.dateUtils.toDate(lastConnection.actuelConnection);

        copy.lastConnection = this.dateUtils.toDate(lastConnection.lastConnection);
        return copy;
    }
}
