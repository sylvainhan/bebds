import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserDeviceWebae } from './user-device-webae.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserDeviceWebae>;

@Injectable()
export class UserDeviceWebaeService {

    private resourceUrl =  SERVER_API_URL + 'api/user-devices';

    constructor(private http: HttpClient) { }

    create(userDevice: UserDeviceWebae): Observable<EntityResponseType> {
        const copy = this.convert(userDevice);
        return this.http.post<UserDeviceWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userDevice: UserDeviceWebae): Observable<EntityResponseType> {
        const copy = this.convert(userDevice);
        return this.http.put<UserDeviceWebae>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserDeviceWebae>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserDeviceWebae[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserDeviceWebae[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserDeviceWebae[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserDeviceWebae = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserDeviceWebae[]>): HttpResponse<UserDeviceWebae[]> {
        const jsonResponse: UserDeviceWebae[] = res.body;
        const body: UserDeviceWebae[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserDeviceWebae.
     */
    private convertItemFromServer(userDevice: UserDeviceWebae): UserDeviceWebae {
        const copy: UserDeviceWebae = Object.assign({}, userDevice);
        return copy;
    }

    /**
     * Convert a UserDeviceWebae to a JSON which can be sent to the server.
     */
    private convert(userDevice: UserDeviceWebae): UserDeviceWebae {
        const copy: UserDeviceWebae = Object.assign({}, userDevice);
        return copy;
    }
}
