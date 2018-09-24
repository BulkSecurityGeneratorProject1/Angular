/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PropMgmntTestModule } from '../../../test.module';
import { DepositDeleteDialogComponent } from 'app/entities/deposit/deposit-delete-dialog.component';
import { DepositService } from 'app/entities/deposit/deposit.service';

describe('Component Tests', () => {
    describe('Deposit Management Delete Component', () => {
        let comp: DepositDeleteDialogComponent;
        let fixture: ComponentFixture<DepositDeleteDialogComponent>;
        let service: DepositService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [DepositDeleteDialogComponent]
            })
                .overrideTemplate(DepositDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepositDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
