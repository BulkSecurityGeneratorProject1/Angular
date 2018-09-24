import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMflapartment } from 'app/shared/model/mflapartment.model';
import { MflapartmentService } from './mflapartment.service';

@Component({
    selector: 'jhi-mflapartment-delete-dialog',
    templateUrl: './mflapartment-delete-dialog.component.html'
})
export class MflapartmentDeleteDialogComponent {
    mflapartment: IMflapartment;

    constructor(
        private mflapartmentService: MflapartmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mflapartmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mflapartmentListModification',
                content: 'Deleted an mflapartment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mflapartment-delete-popup',
    template: ''
})
export class MflapartmentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mflapartment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MflapartmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.mflapartment = mflapartment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
