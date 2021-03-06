import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApartment } from 'app/shared/model/apartment.model';

type EntityResponseType = HttpResponse<IApartment>;
type EntityArrayResponseType = HttpResponse<IApartment[]>;

@Injectable({ providedIn: 'root' })
export class ApartmentService {
    private resourceUrl = SERVER_API_URL + 'api/apartments';

    constructor(private http: HttpClient) {}

    create(apartment: IApartment): Observable<EntityResponseType> {
        return this.http.post<IApartment>(this.resourceUrl, apartment, { observe: 'response' });
    }

    update(apartment: IApartment): Observable<EntityResponseType> {
        return this.http.put<IApartment>(this.resourceUrl, apartment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApartment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApartment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
