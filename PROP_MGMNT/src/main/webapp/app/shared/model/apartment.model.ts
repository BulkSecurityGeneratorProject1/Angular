import { IInvoice } from 'app/shared/model//invoice.model';
import { IDeposit } from 'app/shared/model//deposit.model';
import { IAgreement } from 'app/shared/model//agreement.model';
import { IMflapartment } from 'app/shared/model//mflapartment.model';

export const enum UnitType {
    OnePlusOne = 'OnePlusOne',
    TwoPlusTwo = 'TwoPlusTwo',
    SinglePlusOne = 'SinglePlusOne'
}

export const enum ApartmentStatus {
    Available = 'Available',
    Booked = 'Booked',
    Rented = 'Rented'
}

export interface IApartment {
    id?: number;
    unit?: string;
    type?: UnitType;
    unitStatus?: ApartmentStatus;
    invoices?: IInvoice[];
    deposits?: IDeposit[];
    agreement?: IAgreement;
    mflapartment?: IMflapartment;
}

export class Apartment implements IApartment {
    constructor(
        public id?: number,
        public unit?: string,
        public type?: UnitType,
        public unitStatus?: ApartmentStatus,
        public invoices?: IInvoice[],
        public deposits?: IDeposit[],
        public agreement?: IAgreement,
        public mflapartment?: IMflapartment
    ) {}
}
