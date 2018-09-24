import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAgreement } from 'app/shared/model/agreement.model';

@Component({
    selector: 'jhi-agreement-detail',
    templateUrl: './agreement-detail.component.html'
})
export class AgreementDetailComponent implements OnInit {
    agreement: IAgreement;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ agreement }) => {
            this.agreement = agreement;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
