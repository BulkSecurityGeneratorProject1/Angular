import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Tenant } from 'app/shared/model/tenant.model';
import { TenantService } from './tenant.service';
import { TenantComponent } from './tenant.component';
import { TenantDetailComponent } from './tenant-detail.component';
import { TenantUpdateComponent } from './tenant-update.component';
import { TenantDeletePopupComponent } from './tenant-delete-dialog.component';
import { ITenant } from 'app/shared/model/tenant.model';

@Injectable({ providedIn: 'root' })
export class TenantResolve implements Resolve<ITenant> {
    constructor(private service: TenantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((tenant: HttpResponse<Tenant>) => tenant.body);
        }
        return Observable.of(new Tenant());
    }
}

export const tenantRoute: Routes = [
    {
        path: 'tenant',
        component: TenantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.tenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tenant/:id/view',
        component: TenantDetailComponent,
        resolve: {
            tenant: TenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.tenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tenant/new',
        component: TenantUpdateComponent,
        resolve: {
            tenant: TenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.tenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tenant/:id/edit',
        component: TenantUpdateComponent,
        resolve: {
            tenant: TenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.tenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tenantPopupRoute: Routes = [
    {
        path: 'tenant/:id/delete',
        component: TenantDeletePopupComponent,
        resolve: {
            tenant: TenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.tenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
