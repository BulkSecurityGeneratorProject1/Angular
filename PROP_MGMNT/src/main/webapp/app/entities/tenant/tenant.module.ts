import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropMgmntSharedModule } from 'app/shared';
import { PropMgmntAdminModule } from 'app/admin/admin.module';
import {
    TenantComponent,
    TenantDetailComponent,
    TenantUpdateComponent,
    TenantDeletePopupComponent,
    TenantDeleteDialogComponent,
    tenantRoute,
    tenantPopupRoute
} from './';

const ENTITY_STATES = [...tenantRoute, ...tenantPopupRoute];

@NgModule({
    imports: [PropMgmntSharedModule, PropMgmntAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TenantComponent, TenantDetailComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
    entryComponents: [TenantComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntTenantModule {}
