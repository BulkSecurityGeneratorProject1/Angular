import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApartment } from 'app/shared/model/apartment.model';
import { Principal } from 'app/core';
import { ApartmentService } from './apartment.service';

@Component({
    selector: 'jhi-apartment',
    templateUrl: './apartment.component.html'
})
export class ApartmentComponent implements OnInit, OnDestroy {
    apartments: IApartment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private apartmentService: ApartmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.apartmentService.query().subscribe(
            (res: HttpResponse<IApartment[]>) => {
                this.apartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApartments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApartment) {
        return item.id;
    }

    registerChangeInApartments() {
        this.eventSubscriber = this.eventManager.subscribe('apartmentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
