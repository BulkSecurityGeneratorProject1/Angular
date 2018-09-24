import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Deposit } from 'app/shared/model/deposit.model';
import { DepositService } from './deposit.service';
import { DepositComponent } from './deposit.component';
import { DepositDetailComponent } from './deposit-detail.component';
import { DepositUpdateComponent } from './deposit-update.component';
import { DepositDeletePopupComponent } from './deposit-delete-dialog.component';
import { IDeposit } from 'app/shared/model/deposit.model';

@Injectable({ providedIn: 'root' })
export class DepositResolve implements Resolve<IDeposit> {
    constructor(private service: DepositService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((deposit: HttpResponse<Deposit>) => deposit.body);
        }
        return Observable.of(new Deposit());
    }
}

export const depositRoute: Routes = [
    {
        path: 'deposit',
        component: DepositComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.deposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deposit/:id/view',
        component: DepositDetailComponent,
        resolve: {
            deposit: DepositResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.deposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deposit/new',
        component: DepositUpdateComponent,
        resolve: {
            deposit: DepositResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.deposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deposit/:id/edit',
        component: DepositUpdateComponent,
        resolve: {
            deposit: DepositResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.deposit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depositPopupRoute: Routes = [
    {
        path: 'deposit/:id/delete',
        component: DepositDeletePopupComponent,
        resolve: {
            deposit: DepositResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'propMgmntApp.deposit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
