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
            text: 'MonthWise Income Distribution',
            display: true
        },
        scales: {
            yAxes: [{ id: 'y-axis-1', type: 'linear', position: 'left', ticks: { min: 0 } }]
        }
    };

    public barchartdata = [0];
    public barChartLabels: string[] = [];

    public expbarChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        title: {
            text: 'MonthWise Expense Distribution',
            display: true
        },
        scales: {
            yAxes: [{ id: 'y-axis-1', type: 'linear', position: 'left', ticks: { min: 0 } }]
        }
    };

    public expbarchartdata = [0];
    public expbarChartLabels: string[] = [];

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
                for (let i = res.body.monthWiseIncomeStatistics.length - 1; i >= 0; i--) {
                    this.barchartdata.push(res.body.monthWiseIncomeStatistics[i].actualTotal);
                    this.barChartLabels.push(res.body.monthWiseIncomeStatistics[i].monthText);
                }

                this.expbarchartdata = [];
                for (let i = res.body.monthWiseExpenseStatistics.length - 1; i >= 0; i--) {
                    this.expbarchartdata.push(res.body.monthWiseExpenseStatistics[i].actualTotal);
                    this.expbarChartLabels.push(res.body.monthWiseExpenseStatistics[i].monthText);
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
