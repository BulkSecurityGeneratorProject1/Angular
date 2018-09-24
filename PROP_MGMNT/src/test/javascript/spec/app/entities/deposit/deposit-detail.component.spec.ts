/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropMgmntTestModule } from '../../../test.module';
import { DepositDetailComponent } from 'app/entities/deposit/deposit-detail.component';
import { Deposit } from 'app/shared/model/deposit.model';

describe('Component Tests', () => {
    describe('Deposit Management Detail Component', () => {
        let comp: DepositDetailComponent;
        let fixture: ComponentFixture<DepositDetailComponent>;
        const route = ({ data: of({ deposit: new Deposit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [DepositDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepositDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepositDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deposit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
