import { Component, OnInit } from '@angular/core';
import {ProviderService} from './/provider.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ActivatedRoute} from '@angular/router';

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
    private filterType = 1;

    constructor(
        private providerService: ProviderService,
        private route: ActivatedRoute,
        private modalService: NgbModal) {
        this.getProviders();
    }

    ngOnInit(): void {
        this.filterType = parseInt(this.route.snapshot.params.type, 10);
    }

    async getProviders() {
        let providers = await this.providerService.getProviders();
        providers = providers.filter(provider => provider.type === this.filterType);
        providers = providers.sort((a, b) => {
            return (a['name'].toLowerCase() > b['name'].toLowerCase()) ? 1 : -1;
        });
        this.providers = providers;
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
