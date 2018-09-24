import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMflapartment } from 'app/shared/model/mflapartment.model';
import { MflapartmentService } from './mflapartment.service';

@Component({
    selector: 'jhi-mflapartment-update',
    templateUrl: './mflapartment-update.component.html'
})
export class MflapartmentUpdateComponent implements OnInit {
    private _mflapartment: IMflapartment;
    isSaving: boolean;

    constructor(private mflapartmentService: MflapartmentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mflapartment }) => {
            this.mflapartment = mflapartment;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mflapartment.id !== undefined) {
            this.subscribeToSaveResponse(this.mflapartmentService.update(this.mflapartment));
        } else {
            this.subscribeToSaveResponse(this.mflapartmentService.create(this.mflapartment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMflapartment>>) {
        result.subscribe((res: HttpResponse<IMflapartment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get mflapartment() {
        return this._mflapartment;
    }

    set mflapartment(mflapartment: IMflapartment) {
        this._mflapartment = mflapartment;
    }
}
