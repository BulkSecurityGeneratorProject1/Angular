import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PropMgmntMflapartmentModule } from './mflapartment/mflapartment.module';
import { PropMgmntTenantModule } from './tenant/tenant.module';
import { PropMgmntAgreementModule } from './agreement/agreement.module';
import { PropMgmntApartmentModule } from './apartment/apartment.module';
import { PropMgmntInvoiceModule } from './invoice/invoice.module';
import { PropMgmntDepositModule } from './deposit/deposit.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PropMgmntMflapartmentModule,
        PropMgmntTenantModule,
        PropMgmntAgreementModule,
        PropMgmntApartmentModule,
        PropMgmntInvoiceModule,
        PropMgmntDepositModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntEntityModule {}
