import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropMgmntSharedModule } from 'app/shared';
import {
    DepositComponent,
    DepositDetailComponent,
    DepositUpdateComponent,
    DepositDeletePopupComponent,
    DepositDeleteDialogComponent,
    depositRoute,
    depositPopupRoute
} from './';

const ENTITY_STATES = [...depositRoute, ...depositPopupRoute];

@NgModule({
    imports: [PropMgmntSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepositComponent,
        DepositDetailComponent,
        DepositUpdateComponent,
        DepositDeleteDialogComponent,
        DepositDeletePopupComponent
    ],
    entryComponents: [DepositComponent, DepositUpdateComponent, DepositDeleteDialogComponent, DepositDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntDepositModule {}
