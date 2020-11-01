import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LodgingListService } from './lodging-list.service'

@Component({
  selector: 'app-lodging-list',
  templateUrl: './lodging-list.component.html',
  styleUrls: ['./lodging-list.component.scss']
})
export class LodgingListComponent implements OnInit {


  public lodgings = [];
  public lodgingId = null;

  public showMessage = false;
  public message = '';
  public errorMessage = false;

  constructor(
      private lodgingListService: LodgingListService,
      private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.getlodgings();

  }

  async getlodgings() {
      this.lodgings = await this.lodgingListService.getlodgings();
      /*this.transports.sort((a, b) => {
          return (a['nombreActividad'].toLowerCase() > b['nombreActividad'].toLowerCase()) ? 1 : -1;
      });*/
  }

  open(content, lodgingId) {
      this.lodgingId = lodgingId;
      this.modalService.open(content);
  }

  async delete() {
      const delResult = await this.lodgingListService.deleteLodging(this.lodgingId);
      if (delResult['status']) {
          this.showMessage = true;
          this.message = 'Servicio de transporte  eliminado exitosamente';
          window.scrollTo(0, 0);
          this.getlodgings();
      } else {
          this.showMessage = true;
          this.errorMessage = true;
          this.message = 'Ha ocurrido un error eliminando el servicio de transporte';
          window.scrollTo(0, 0);
      }
      this.modalService.dismissAll();
  }

}
