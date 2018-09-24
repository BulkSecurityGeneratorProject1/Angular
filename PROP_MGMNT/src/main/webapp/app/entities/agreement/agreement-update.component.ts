import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';
import { IApartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from 'app/entities/apartment';
import { ITenant } from 'app/shared/model/tenant.model';
import { TenantService } from 'app/entities/tenant';

@Component({
    selector: 'jhi-agreement-update',
    templateUrl: './agreement-update.component.html'
})
export class AgreementUpdateComponent implements OnInit {
    private _agreement: IAgreement;
    isSaving: boolean;

    apartments: IApartment[];

    tenants: ITenant[];
    agreementStartDateDp: any;
    agreementEndDateDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private agreementService: AgreementService,
        private apartmentService: ApartmentService,
        private tenantService: TenantService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ agreement }) => {
            this.agreement = agreement;
        });
        this.apartmentService.query({ filter: 'agreement-is-null' }).subscribe(
            (res: HttpResponse<IApartment[]>) => {
                if (!this.agreement.apartment || !this.agreement.apartment.id) {
                    this.apartments = res.body;
                } else {
                    this.apartmentService.find(this.agreement.apartment.id).subscribe(
                        (subRes: HttpResponse<IApartment>) => {
                            this.apartments = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tenantService.query({ filter: 'agreement-is-null' }).subscribe(
            (res: HttpResponse<ITenant[]>) => {
                if (!this.agreement.tenant || !this.agreement.tenant.id) {
                    this.tenants = res.body;
                } else {
                    this.tenantService.find(this.agreement.tenant.id).subscribe(
                        (subRes: HttpResponse<ITenant>) => {
                            this.tenants = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.agreement.id !== undefined) {
            this.subscribeToSaveResponse(this.agreementService.update(this.agreement));
        } else {
            this.subscribeToSaveResponse(this.agreementService.create(this.agreement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAgreement>>) {
        result.subscribe((res: HttpResponse<IAgreement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApartmentById(index: number, item: IApartment) {
        return item.id;
    }

    trackTenantById(index: number, item: ITenant) {
        return item.id;
    }
    get agreement() {
        return this._agreement;
    }

    set agreement(agreement: IAgreement) {
        this._agreement = agreement;
    }
}
