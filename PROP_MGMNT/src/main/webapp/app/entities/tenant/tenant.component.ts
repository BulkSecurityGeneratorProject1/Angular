import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITenant } from 'app/shared/model/tenant.model';
import { Principal } from 'app/core';
import { TenantService } from './tenant.service';

@Component({
    selector: 'jhi-tenant',
    templateUrl: './tenant.component.html'
})
export class TenantComponent implements OnInit, OnDestroy {
    tenants: ITenant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tenantService: TenantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tenantService.query().subscribe(
            (res: HttpResponse<ITenant[]>) => {
                this.tenants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTenants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITenant) {
        return item.id;
    }

    registerChangeInTenants() {
        this.eventSubscriber = this.eventManager.subscribe('tenantListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
