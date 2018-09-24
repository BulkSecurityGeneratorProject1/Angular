/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PropMgmntTestModule } from '../../../test.module';
import { MflapartmentUpdateComponent } from 'app/entities/mflapartment/mflapartment-update.component';
import { MflapartmentService } from 'app/entities/mflapartment/mflapartment.service';
import { Mflapartment } from 'app/shared/model/mflapartment.model';

describe('Component Tests', () => {
    describe('Mflapartment Management Update Component', () => {
        let comp: MflapartmentUpdateComponent;
        let fixture: ComponentFixture<MflapartmentUpdateComponent>;
        let service: MflapartmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [MflapartmentUpdateComponent]
            })
                .overrideTemplate(MflapartmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MflapartmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MflapartmentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Mflapartment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mflapartment = entity;
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
                    const entity = new Mflapartment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mflapartment = entity;
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
