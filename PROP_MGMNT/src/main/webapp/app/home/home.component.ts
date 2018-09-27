import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { InvoiceService } from '../entities/invoice/invoice.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { LoginModalService, Principal, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    public monthlydata = [100, 500];
    public yearlydata = [100, 500];
    public monthlyChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            text: 'Monthly Income/Expense Distribution',
            display: true
        }
    };

    public yearlyChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            text: 'Yearly Income/Expense Distribution',
            display: true
        }
    };
    public monthlyChartLabels: string[] = ['Income', 'Expense'];

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            text: 'Yearly Income/Expense Distribution',
            display: true
        }
    };

    public barchartdata = [0];
    public barChartLabels: string[] = [];

    /*public lineChartData:any[] = [
        {data: [65], label: 'Series A'},
        {data: [28], label: 'Series B'}
        ];*/

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private invoiceService: InvoiceService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
            this.getIncomeExpenseForThisMonth();
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
                this.getIncomeExpenseForThisMonth();
            });
        });
    }

    getIncomeExpenseForThisMonth() {
        this.invoiceService.thisMonth().subscribe(
            (res: any) => {
                this.monthlydata = [];
                this.monthlydata.push(res.body.totalMonthlyIncome);
                this.monthlydata.push(res.body.totalMonthlyExpense);

                this.yearlydata = [];
                this.yearlydata.push(res.body.totalYearlyIncome);
                this.yearlydata.push(res.body.totalYearlyExpense);
                //this.monthlyChartOptions.Options.title.text = "September";

                this.barchartdata = [];
                for (let i = res.body.monthWiseIncomeExpenseStatistics.length - 1; i >= 0; i--) {
                    this.barchartdata.push(res.body.monthWiseIncomeExpenseStatistics[i].actualTotal);
                    this.barChartLabels.push(res.body.monthWiseIncomeExpenseStatistics[i].month);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
