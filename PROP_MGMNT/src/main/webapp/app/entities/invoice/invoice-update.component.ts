import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { IApartment } from 'app/shared/model/apartment.model';
import { ApartmentService } from 'app/entities/apartment';
import { PayPalConfig, PayPalEnvironment, PayPalIntegrationType } from 'ngx-paypal';
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

    constructor(
        private jhiAlertService: JhiAlertService,
        private invoiceService: InvoiceService,
        private apartmentService: ApartmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.initConfig();
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invoice }) => {
            this.invoice = invoice;
        });
        this.apartmentService.query().subscribe(
            (res: HttpResponse<IApartment[]>) => {
                this.apartments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
                        total: 30.11,
                        currency: 'USD',
                        details: {
                            subtotal: 30.0,
                            tax: 0.07,
                            shipping: 0.03,
                            handling_fee: 1.0,
                            shipping_discount: -1.0,
                            insurance: 0.01
                        }
                    },
                    custom: 'Custom value',
                    item_list: {
                        items: [
                            {
                                name: 'hat',
                                description: 'Brown hat.',
                                quantity: 5,
                                price: 3,
                                tax: 0.01,
                                sku: '1',
                                currency: 'USD'
                            },
                            {
                                name: 'handbag',
                                description: 'Black handbag.',
                                quantity: 1,
                                price: 15,
                                tax: 0.02,
                                sku: 'product34',
                                currency: 'USD'
                            }
                        ],
                        shipping_address: {
                            recipient_name: 'Brian Robinson',
                            line1: '4th Floor',
                            line2: 'Unit #34',
                            city: 'San Jose',
                            country_code: 'US',
                            postal_code: '95131',
                            phone: '011862212345678',
                            state: 'CA'
                        }
                    }
                }
            ],
            note_to_payer: 'Contact us if you have troubles processing payment'
        });
    }
}
