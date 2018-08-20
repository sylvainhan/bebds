import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserPreferenceWebae } from './user-and-preference-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserPreferenceWebae>;

@Injectable()
export class UserPreferenceWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/user-and-preferences';

    constructor(private http: HttpClient) { }

    create(userPreference: UserPreferenceWebae): Observable<EntityResponseType> {
        const copy = this.convert(userPreference);
        return this.http.post<UserPreferenceWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userPreference: UserPreferenceWebae): Observable<EntityResponseType> {
        const copy = this.convert(userPreference);
        return this.http.put<UserPreferenceWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserPreferenceWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserPreferenceWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserPreferenceWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserPreferenceWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserPreferenceWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserPreferenceWebae[]>): HttpResponse<UserPreferenceWebae[]> {
        const jsonResponse: UserPreferenceWebae[] = res.body;
        const body: UserPreferenceWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserPreferenceWebae.
     */
    private convertItemFromServer(userPreference: UserPreferenceWebae): UserPreferenceWebae {
        const copy: UserPreferenceWebae = Object.assign({}, userPreference);
        return copy;
    }

    /**
     * Convert a UserPreferenceWebae to a JSON which can be sent to the server.
     */
    private convert(userPreference: UserPreferenceWebae): UserPreferenceWebae {
        const copy: UserPreferenceWebae = Object.assign({}, userPreference);
        return copy;
    }
}
