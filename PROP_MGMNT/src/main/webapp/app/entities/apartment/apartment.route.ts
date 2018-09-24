import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Apartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from './apartment.service';
import { ApartmentComponent } from './apartment.component';
import { ApartmentDetailComponent } from './apartment-detail.component';
import { ApartmentUpdateComponent } from './apartment-update.component';
import { ApartmentDeletePopupComponent } from './apartment-delete-dialog.component';
import { IApartment } from 'app/shared/model/apartment.model';

@Injectable({ providedIn: 'root' })
export class ApartmentResolve implements Resolve<IApartment> {
    constructor(private service: ApartmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((apartment: HttpResponse<Apartment>) => apartment.body);
        }
        return Observable.of(new Apartment());
    }
}

export const apartmentRoute: Routes = [
    {
        path: 'apartment',
        component: ApartmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment/:id/view',
        component: ApartmentDetailComponent,
        resolve: {
            apartment: ApartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment/new',
        component: ApartmentUpdateComponent,
        resolve: {
            apartment: ApartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apartment/:id/edit',
        component: ApartmentUpdateComponent,
        resolve: {
            apartment: ApartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apartmentPopupRoute: Routes = [
    {
        path: 'apartment/:id/delete',
        component: ApartmentDeletePopupComponent,
        resolve: {
            apartment: ApartmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.apartment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
