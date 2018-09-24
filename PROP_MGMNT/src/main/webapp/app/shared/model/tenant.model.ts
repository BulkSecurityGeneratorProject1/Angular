import { IUser } from 'app/core/user/user.model';
import { IAgreement } from 'app/shared/model//agreement.model';

export interface ITenant {
    id?: number;
    name?: string;
    email?: string;
    uniqueIdentifier?: string;
    address?: string;
    user?: IUser;
    agreement?: IAgreement;
}

export class Tenant implements ITenant {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public uniqueIdentifier?: string,
        public address?: string,
        public user?: IUser,
        public agreement?: IAgreement
    ) {}
}
