import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Mflapartment } from 'app/shared/model/mflapartment.model';
import { MflapartmentService } from './mflapartment.service';
import { MflapartmentComponent } from './mflapartment.component';
import { MflapartmentDetailComponent } from './mflapartment-detail.component';
import { MflapartmentUpdateComponent } from './mflapartment-update.component';
import { MflapartmentDeletePopupComponent } from './mflapartment-delete-dialog.component';
import { IMflapartment } from 'app/shared/model/mflapartment.model';

@Injectable({ providedIn: 'root' })
export class MflapartmentResolve implements Resolve<IMflapartment> {
    constructor(private service: MflapartmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((mflapartment: HttpResponse<Mflapartment>) => mflapartment.body);
        }
        return Observable.of(new Mflapartment());
    }
}

export const mflapartmentRoute: Routes = [
    {
        path: 'mflapartment',
        component: MflapartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.mflapartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mflapartment/:id/view',
        component: MflapartmentDetailComponent,
        resolve: {
            mflapartment: MflapartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.mflapartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mflapartment/new',
        component: MflapartmentUpdateComponent,
        resolve: {
            mflapartment: MflapartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.mflapartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mflapartment/:id/edit',
        component: MflapartmentUpdateComponent,
        resolve: {
            mflapartment: MflapartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.mflapartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mflapartmentPopupRoute: Routes = [
    {
        path: 'mflapartment/:id/delete',
        component: MflapartmentDeletePopupComponent,
        resolve: {
            mflapartment: MflapartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.mflapartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
