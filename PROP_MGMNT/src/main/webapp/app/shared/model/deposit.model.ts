import { Moment } from 'moment';
import { IApartment } from 'app/shared/model//apartment.model';

export const enum DepositType {
    Loan = 'Loan',
    Insurance = 'Insurance'
}

export const enum DepositStatus {
    Initiated = 'Initiated',
    Approved = 'Approved'
}

export interface IDeposit {
    id?: number;
    initiatedDate?: Moment;
    approvedDate?: Moment;
    type?: DepositType;
    amount?: number;
    depStatus?: DepositStatus;
    apartment?: IApartment;
}

export class Deposit implements IDeposit {
    constructor(
        public id?: number,
        public initiatedDate?: Moment,
        public approvedDate?: Moment,
        public type?: DepositType,
        public amount?: number,
        public depStatus?: DepositStatus,
        public apartment?: IApartment
    ) {}
}
