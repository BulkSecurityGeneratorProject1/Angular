import { Moment } from 'moment';
import { IApartment } from 'app/shared/model//apartment.model';
import { ITenant } from 'app/shared/model//tenant.model';

export interface IAgreement {
    id?: number;
    agreementStartDate?: Moment;
    agreementEndDate?: Moment;
    agreementDetailsContentType?: string;
    agreementDetails?: any;
    apartment?: IApartment;
    tenant?: ITenant;
}

export class Agreement implements IAgreement {
    constructor(
        public id?: number,
        public agreementStartDate?: Moment,
        public agreementEndDate?: Moment,
        public agreementDetailsContentType?: string,
        public agreementDetails?: any,
        public apartment?: IApartment,
        public tenant?: ITenant
    ) {}
}
