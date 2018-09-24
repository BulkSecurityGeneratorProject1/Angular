import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropMgmntSharedModule } from 'app/shared';
import {
    ApartmentComponent,
    ApartmentDetailComponent,
    ApartmentUpdateComponent,
    ApartmentDeletePopupComponent,
    ApartmentDeleteDialogComponent,
    apartmentRoute,
    apartmentPopupRoute
} from './';

const ENTITY_STATES = [...apartmentRoute, ...apartmentPopupRoute];

@NgModule({
    imports: [PropMgmntSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApartmentComponent,
        ApartmentDetailComponent,
        ApartmentUpdateComponent,
        ApartmentDeleteDialogComponent,
        ApartmentDeletePopupComponent
    ],
    entryComponents: [ApartmentComponent, ApartmentUpdateComponent, ApartmentDeleteDialogComponent, ApartmentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntApartmentModule {}
