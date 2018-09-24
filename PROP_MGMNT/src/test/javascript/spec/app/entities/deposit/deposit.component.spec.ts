/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropMgmntTestModule } from '../../../test.module';
import { DepositComponent } from 'app/entities/deposit/deposit.component';
import { DepositService } from 'app/entities/deposit/deposit.service';
import { Deposit } from 'app/shared/model/deposit.model';

describe('Component Tests', () => {
    describe('Deposit Management Component', () => {
        let comp: DepositComponent;
        let fixture: ComponentFixture<DepositComponent>;
        let service: DepositService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [DepositComponent],
                providers: []
            })
                .overrideTemplate(DepositComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepositComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Deposit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deposits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
