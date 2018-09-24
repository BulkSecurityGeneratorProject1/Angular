import { Moment } from 'moment';
import { IApartment } from 'app/shared/model//apartment.model';

export const enum InvoiceType {
    Income = 'Income',
    Expense = 'Expense'
}

export const enum IncomeType {
    Rent = 'Rent',
    Laundry = 'Laundry',
    Parking = 'Parking',
    Storage = 'Storage',
    Others = 'Others'
}

export const enum ExpenseType {
    Taxes = 'Taxes',
    Insurance = 'Insurance',
    Utility = 'Utility',
    Maintainance = 'Maintainance',
    StaffSalary = 'StaffSalary',
    Repairs = 'Repairs',
    Others = 'Others'
}

export const enum InvoiceStatus {
    Generated = 'Generated',
    Paid = 'Paid'
}

export interface IInvoice {
    id?: number;
    generatedDate?: Moment;
    paidDate?: Moment;
    type?: InvoiceType;
    incomeCategory?: IncomeType;
    expenseCategory?: ExpenseType;
    amount?: number;
    invStatus?: InvoiceStatus;
    apartment?: IApartment;
}

export class Invoice implements IInvoice {
    constructor(
        public id?: number,
        public generatedDate?: Moment,
        public paidDate?: Moment,
        public type?: InvoiceType,
        public incomeCategory?: IncomeType,
        public expenseCategory?: ExpenseType,
        public amount?: number,
        public invStatus?: InvoiceStatus,
        public apartment?: IApartment
    ) {}
}
