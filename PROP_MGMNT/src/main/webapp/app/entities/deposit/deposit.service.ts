import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeposit } from 'app/shared/model/deposit.model';

type EntityResponseType = HttpResponse<IDeposit>;
type EntityArrayResponseType = HttpResponse<IDeposit[]>;

@Injectable({ providedIn: 'root' })
export class DepositService {
    private resourceUrl = SERVER_API_URL + 'api/deposits';

    constructor(private http: HttpClient) {}

    create(deposit: IDeposit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deposit);
        return this.http
            .post<IDeposit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(deposit: IDeposit): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deposit);
        return this.http
            .put<IDeposit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDeposit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDeposit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(deposit: IDeposit): IDeposit {
        const copy: IDeposit = Object.assign({}, deposit, {
            initiatedDate:
                deposit.initiatedDate != null && deposit.initiatedDate.isValid() ? deposit.initiatedDate.format(DATE_FORMAT) : null,
            approvedDate: deposit.approvedDate != null && deposit.approvedDate.isValid() ? deposit.approvedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.initiatedDate = res.body.initiatedDate != null ? moment(res.body.initiatedDate) : null;
        res.body.approvedDate = res.body.approvedDate != null ? moment(res.body.approvedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((deposit: IDeposit) => {
            deposit.initiatedDate = deposit.initiatedDate != null ? moment(deposit.initiatedDate) : null;
            deposit.approvedDate = deposit.approvedDate != null ? moment(deposit.approvedDate) : null;
        });
        return res;
    }
}
