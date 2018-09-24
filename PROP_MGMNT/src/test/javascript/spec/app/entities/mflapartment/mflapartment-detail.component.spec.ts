/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PropMgmntTestModule } from '../../../test.module';
import { MflapartmentDetailComponent } from 'app/entities/mflapartment/mflapartment-detail.component';
import { Mflapartment } from 'app/shared/model/mflapartment.model';

describe('Component Tests', () => {
    describe('Mflapartment Management Detail Component', () => {
        let comp: MflapartmentDetailComponent;
        let fixture: ComponentFixture<MflapartmentDetailComponent>;
        const route = ({ data: of({ mflapartment: new Mflapartment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [MflapartmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MflapartmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MflapartmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mflapartment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
