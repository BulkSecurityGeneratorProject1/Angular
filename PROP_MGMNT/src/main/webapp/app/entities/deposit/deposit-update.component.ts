import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDeposit } from 'app/shared/model/deposit.model';
import { DepositService } from './deposit.service';
import { IApartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from 'app/entities/apartment';

@Component({
    selector: 'jhi-deposit-update',
    templateUrl: './deposit-update.component.html'
})
export class DepositUpdateComponent implements OnInit {
    private _deposit: IDeposit;
    isSaving: boolean;

    apartments: IApartment[];
    initiatedDateDp: any;
    approvedDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private depositService: DepositService,
        private apartmentService: ApartmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deposit }) => {
            this.deposit = deposit;
        });
        this.apartmentService.query().subscribe(
            (res: HttpResponse<IApartment[]>) => {
                this.apartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deposit.id !== undefined) {
            this.subscribeToSaveResponse(this.depositService.update(this.deposit));
        } else {
            this.subscribeToSaveResponse(this.depositService.create(this.deposit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeposit>>) {
        result.subscribe((res: HttpResponse<IDeposit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get deposit() {
        return this._deposit;
    }

    set deposit(deposit: IDeposit) {
        this._deposit = deposit;
    }
}
