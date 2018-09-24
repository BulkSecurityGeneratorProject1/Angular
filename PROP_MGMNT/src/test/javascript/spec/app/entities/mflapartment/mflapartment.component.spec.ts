/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PropMgmntTestModule } from '../../../test.module';
import { MflapartmentComponent } from 'app/entities/mflapartment/mflapartment.component';
import { MflapartmentService } from 'app/entities/mflapartment/mflapartment.service';
import { Mflapartment } from 'app/shared/model/mflapartment.model';

describe('Component Tests', () => {
    describe('Mflapartment Management Component', () => {
        let comp: MflapartmentComponent;
        let fixture: ComponentFixture<MflapartmentComponent>;
        let service: MflapartmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PropMgmntTestModule],
                declarations: [MflapartmentComponent],
                providers: []
            })
                .overrideTemplate(MflapartmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MflapartmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MflapartmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Mflapartment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mflapartments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
