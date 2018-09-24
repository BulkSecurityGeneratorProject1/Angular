import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMflapartment } from 'app/shared/model/mflapartment.model';

type EntityResponseType = HttpResponse<IMflapartment>;
type EntityArrayResponseType = HttpResponse<IMflapartment[]>;

@Injectable({ providedIn: 'root' })
export class MflapartmentService {
    private resourceUrl = SERVER_API_URL + 'api/mflapartments';

    constructor(private http: HttpClient) {}

    create(mflapartment: IMflapartment): Observable<EntityResponseType> {
        return this.http.post<IMflapartment>(this.resourceUrl, mflapartment, { observe: 'response' });
    }

    update(mflapartment: IMflapartment): Observable<EntityResponseType> {
        return this.http.put<IMflapartment>(this.resourceUrl, mflapartment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMflapartment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMflapartment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
