import { Component, OnInit } from '@angular/core';
import {ProviderService} from './/provider.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-transport-list',
  templateUrl: './provider-list.component.html',
  styleUrls: ['./provider-list.component.scss']
})
export class ProviderListComponent implements OnInit {


    public providers = [];
    public providerId = null;

    public showMessage = false;
    public message = '';
    public errorMessage = false;

    constructor(
        private providerService: ProviderService,
        private modalService: NgbModal) {
        this.getProviders();
    }

    ngOnInit(): void {
    }

    async getProviders() {
        this.providers = await this.providerService.getProviders();
        /*this.providers.sort((a, b) => {
            return (a['nombreActividad'].toLowerCase() > b['nombreActividad'].toLowerCase()) ? 1 : -1;
        });*/
    }

    open(content, providerId) {
        this.providerId = providerId;
        this.modalService.open(content);
    }

    async delete() {
        const delResult = await this.providerService.deleteProvider(this.providerId);
        if (delResult['status']) {
            this.showMessage = true;
            this.message = 'Servicio de proveedor  eliminado exitosamente';
            window.scrollTo(0, 0);
            this.getProviders();
        } else {
            this.showMessage = true;
            this.errorMessage = true;
            this.message = 'Ha ocurrido un error eliminando el proveedor seleccionado';
            window.scrollTo(0, 0);
        }
        this.modalService.dismissAll();
    }
}
