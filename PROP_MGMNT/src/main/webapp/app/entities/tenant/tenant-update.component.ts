import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { IUser, UserService } from 'app/core';
import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from 'app/entities/agreement';

@Component({
    selector: 'jhi-tenant-update',
    templateUrl: './tenant-update.component.html'
})
export class TenantUpdateComponent implements OnInit {
    private _tenant: ITenant;
    isSaving: boolean;

    users: IUser[];

    agreements: IAgreement[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private tenantService: TenantService,
        private userService: UserService,
        private agreementService: AgreementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tenant }) => {
            this.tenant = tenant;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.agreementService.query().subscribe(
            (res: HttpResponse<IAgreement[]>) => {
                this.agreements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tenant.id !== undefined) {
            this.subscribeToSaveResponse(this.tenantService.update(this.tenant));
        } else {
            this.subscribeToSaveResponse(this.tenantService.create(this.tenant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITenant>>) {
        result.subscribe((res: HttpResponse<ITenant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAgreementById(index: number, item: IAgreement) {
        return item.id;
    }
    get tenant() {
        return this._tenant;
    }

    set tenant(tenant: ITenant) {
        this._tenant = tenant;
    }
}
