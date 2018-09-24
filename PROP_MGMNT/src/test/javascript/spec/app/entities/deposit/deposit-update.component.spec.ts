/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropMgmntTestModule } from '../../../test.module';
import { DepositUpdateComponent } from 'app/entities/deposit/deposit-update.component';
import { DepositService } from 'app/entities/deposit/deposit.service';
import { Deposit } from 'app/shared/model/deposit.model';

describe('Component Tests', () => {
    describe('Deposit Management Update Component', () => {
        let comp: DepositUpdateComponent;
        let fixture: ComponentFixture<DepositUpdateComponent>;
        let service: DepositService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [DepositUpdateComponent]
            })
                .overrideTemplate(DepositUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepositUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Deposit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deposit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Deposit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deposit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
