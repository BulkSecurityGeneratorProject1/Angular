import { IApartment } from 'app/shared/model//apartment.model';

export interface IMflapartment {
    id?: number;
    name?: string;
    owner?: string;
    address?: string;
    loanNumber?: string;
    apartments?: IApartment[];
}

export class Mflapartment implements IMflapartment {
    constructor(
        public id?: number,
        public name?: string,
        public owner?: string,
        public address?: string,
        public loanNumber?: string,
        public apartments?: IApartment[]
    ) {}
}
