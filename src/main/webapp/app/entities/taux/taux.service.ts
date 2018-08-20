import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Taux } from './taux.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Taux>;

@Injectable()
export class TauxService {

    private resourceUrl =  SERVER_API_URL + 'api/tauxes';

    constructor(private http: HttpClient) { }

    create(taux: Taux): Observable<EntityResponseType> {
        const copy = this.convert(taux);
        return this.http.post<Taux>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(taux: Taux): Observable<EntityResponseType> {
        const copy = this.convert(taux);
        return this.http.put<Taux>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Taux>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Taux[]>> {
        const options = createRequestOption(req);
        return this.http.get<Taux[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Taux[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Taux = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Taux[]>): HttpResponse<Taux[]> {
        const jsonResponse: Taux[] = res.body;
        const body: Taux[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Taux.
     */
    private convertItemFromServer(taux: Taux): Taux {
        const copy: Taux = Object.assign({}, taux);
        return copy;
    }

    /**
     * Convert a Taux to a JSON which can be sent to the server.
     */
    private convert(taux: Taux): Taux {
        const copy: Taux = Object.assign({}, taux);
        return copy;
    }
}
