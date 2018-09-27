import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PropMgmntSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { ChartsModule } from 'ng2-charts';

@NgModule({
    imports: [PropMgmntSharedModule, ChartsModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PropMgmntHomeModule {}
