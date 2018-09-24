import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropMgmntSharedModule } from 'app/shared';
import {
    MflapartmentComponent,
    MflapartmentDetailComponent,
    MflapartmentUpdateComponent,
    MflapartmentDeletePopupComponent,
    MflapartmentDeleteDialogComponent,
    mflapartmentRoute,
    mflapartmentPopupRoute
} from './';

const ENTITY_STATES = [...mflapartmentRoute, ...mflapartmentPopupRoute];

@NgModule({
    imports: [PropMgmntSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MflapartmentComponent,
        MflapartmentDetailComponent,
        MflapartmentUpdateComponent,
        MflapartmentDeleteDialogComponent,
        MflapartmentDeletePopupComponent
    ],
    entryComponents: [
        MflapartmentComponent,
        MflapartmentUpdateComponent,
        MflapartmentDeleteDialogComponent,
        MflapartmentDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntMflapartmentModule {}
