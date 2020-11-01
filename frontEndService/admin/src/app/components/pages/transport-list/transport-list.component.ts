import { Component, OnInit } from '@angular/core';
import {TransportService} from '../transport-list/transport.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-transport-list',
  templateUrl: './transport-list.component.html',
  styleUrls: ['./transport-list.component.scss']
})
export class TransportListComponent implements OnInit {


    public transports = [];
    public transportId = null;

    public showMessage = false;
    public message = '';
    public errorMessage = false;

    constructor(
        private transportService: TransportService,
        private modalService: NgbModal) {
        this.gettransports();
    }

    ngOnInit(): void {
    }

    async gettransports() {
        this.transports = await this.transportService.getTransports();
        /*this.transports.sort((a, b) => {
            return (a['nombreActividad'].toLowerCase() > b['nombreActividad'].toLowerCase()) ? 1 : -1;
        });*/
    }

    open(content, transportId) {
        this.transportId = transportId;
        this.modalService.open(content);
    }

    async delete() {
        const delResult = await this.transportService.deleteTransport(this.transportId);
        if (delResult['status']) {
            this.showMessage = true;
            this.message = 'Servicio de transporte  eliminado exitosamente';
            window.scrollTo(0, 0);
            this.gettransports();
        } else {
            this.showMessage = true;
            this.errorMessage = true;
            this.message = 'Ha ocurrido un error eliminando el servicio de transporte';
            window.scrollTo(0, 0);
        }
        this.modalService.dismissAll();
    }
}
    