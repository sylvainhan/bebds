import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DemandeWebae } from './demande-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DemandeWebae>;

@Injectable()
export class DemandeWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/demandes';

    constructor(private http: HttpClient) { }

    create(demande: DemandeWebae): Observable<EntityResponseType> {
        const copy = this.convert(demande);
        return this.http.post<DemandeWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(demande: DemandeWebae): Observable<EntityResponseType> {
        const copy = this.convert(demande);
        return this.http.put<DemandeWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DemandeWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DemandeWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<DemandeWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DemandeWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DemandeWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DemandeWebae[]>): HttpResponse<DemandeWebae[]> {
        const jsonResponse: DemandeWebae[] = res.body;
        const body: DemandeWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DemandeWebae.
     */
    private convertItemFromServer(demande: DemandeWebae): DemandeWebae {
        const copy: DemandeWebae = Object.assign({}, demande);
        return copy;
    }

    /**
     * Convert a DemandeWebae to a JSON which can be sent to the server.
     */
    private convert(demande: DemandeWebae): DemandeWebae {
        const copy: DemandeWebae = Object.assign({}, demande);
        return copy;
    }
}
