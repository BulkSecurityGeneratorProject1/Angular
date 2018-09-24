import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMflapartment } from 'app/shared/model/mflapartment.model';
import { Principal } from 'app/core';
import { MflapartmentService } from './mflapartment.service';

@Component({
    selector: 'jhi-mflapartment',
    templateUrl: './mflapartment.component.html'
})
export class MflapartmentComponent implements OnInit, OnDestroy {
    mflapartments: IMflapartment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mflapartmentService: MflapartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.mflapartmentService.query().subscribe(
            (res: HttpResponse<IMflapartment[]>) => {
                this.mflapartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMflapartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMflapartment) {
        return item.id;
    }

    registerChangeInMflapartments() {
        this.eventSubscriber = this.eventManager.subscribe('mflapartmentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
