import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMflapartment } from 'app/shared/model/mflapartment.model';

@Component({
    selector: 'jhi-mflapartment-detail',
    templateUrl: './mflapartment-detail.component.html'
})
export class MflapartmentDetailComponent implements OnInit {
    mflapartment: IMflapartment;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mflapartment }) => {
            this.mflapartment = mflapartment;
        });
    }

    previousState() {
        window.history.back();
    }
}
