import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { of } from 'rxjs';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { IApartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from 'app/entities/apartment';
import { PayPalConfig, PayPalEnvironment, PayPalIntegrationType } from 'ngx-paypal';
import { InvoiceStatus } from '../../shared/model/invoice.model';
//https://github.com/Enngage/ngx-paypal
@Component({
    selector: 'jhi-invoice-update',
    templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
    private _invoice: IInvoice;
    isSaving: boolean;
    public payPalConfig?: PayPalConfig;

    apartments: IApartment[];
    generatedDateDp: any;
    paidDateDp: any;
    invoiceAmt: number = 9;

    constructor(
        private jhiAlertService: JhiAlertService,
        private invoiceService: InvoiceService,
        private apartmentService: ApartmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoice }) => {
            this.invoice = invoice;
            this.invoiceAmt = this.invoice.amount;
        });
        this.apartmentService.query().subscribe(
            (res: HttpResponse<IApartment[]>) => {
                this.apartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.initConfig();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invoice.id !== undefined) {
            this.subscribeToSaveResponse(this.invoiceService.update(this.invoice));
        } else {
            this.subscribeToSaveResponse(this.invoiceService.create(this.invoice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>) {
        result.subscribe((res: HttpResponse<IInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApartmentById(index: number, item: IApartment) {
        return item.id;
    }
    get invoice() {
        return this._invoice;
    }

    set invoice(invoice: IInvoice) {
        this._invoice = invoice;
    }

    private initConfig(): void {
        this.payPalConfig = new PayPalConfig(PayPalIntegrationType.ClientSideREST, PayPalEnvironment.Sandbox, {
            commit: true,
            client: {
                sandbox: 'AWumh80jmXWP01tMpT37Q7D1zdW9a01nVolrqlRXDejIMua3zAvKuwzs_Hkqla-tIVzL69AHgtmIhSYr'
            },
            button: {
                label: 'paypal',
                layout: 'vertical'
            },
            onAuthorize: (data, actions) => {
                console.log('Authorize');
                return of(undefined);
            },
            onPaymentComplete: (data, actions) => {
                console.log('OnPaymentComplete');
                this.invoice.invStatus = InvoiceStatus.Paid;
                this.save();
            },
            onCancel: (data, actions) => {
                console.log('OnCancel');
            },
            onError: err => {
                console.log('OnError');
            },
            onClick: () => {
                console.log('onClick');
            },
            validate: actions => {
                console.log(actions);
            },
            transactions: [
                {
                    amount: {
                        currency: 'USD',
                        total: this.invoice.amount
                    }
                }
            ],
            note_to_payer: 'Contact us if you have troubles processing payment'
        });
    }
}
