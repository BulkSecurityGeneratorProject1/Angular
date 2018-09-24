import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IApartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from './apartment.service';
import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from 'app/entities/agreement';
import { IMflapartment } from 'app/shared/model/mflapartment.model';
import { MflapartmentService } from 'app/entities/mflapartment';

@Component({
    selector: 'jhi-apartment-update',
    templateUrl: './apartment-update.component.html'
})
export class ApartmentUpdateComponent implements OnInit {
    private _apartment: IApartment;
    isSaving: boolean;

    agreements: IAgreement[];

    mflapartments: IMflapartment[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private apartmentService: ApartmentService,
        private agreementService: AgreementService,
        private mflapartmentService: MflapartmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apartment }) => {
            this.apartment = apartment;
        });
        this.agreementService.query().subscribe(
            (res: HttpResponse<IAgreement[]>) => {
                this.agreements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.mflapartmentService.query().subscribe(
            (res: HttpResponse<IMflapartment[]>) => {
                this.mflapartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.apartment.id !== undefined) {
            this.subscribeToSaveResponse(this.apartmentService.update(this.apartment));
        } else {
            this.subscribeToSaveResponse(this.apartmentService.create(this.apartment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApartment>>) {
        result.subscribe((res: HttpResponse<IApartment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAgreementById(index: number, item: IAgreement) {
        return item.id;
    }

    trackMflapartmentById(index: number, item: IMflapartment) {
        return item.id;
    }
    get apartment() {
        return this._apartment;
    }

    set apartment(apartment: IApartment) {
        this._apartment = apartment;
    }
}
