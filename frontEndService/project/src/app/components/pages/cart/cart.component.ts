import { Component, OnInit } from '@angular/core';
import { CartService } from './cart.service'
import { QuotationDetails } from '../../interfaces/ctemporal.interface';
import { Quotation } from '../../interfaces/quotation.interface';
import { ToastService } from '../Toast/toast-container/toast.service';
import { Email } from "../../interfaces/email.interface";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  subtotal:number;
  iva:number;
  total:number;
  cotizacion: QuotationDetails;
  quotation: Quotation = new Quotation();
  email: Email = new Email();
  constructor( private cartService:CartService,
               public toastService: ToastService,
               private modalService: NgbModal,
               private router: Router
               ) {

  }

  ngOnInit(): void {
    this.cotizacion= JSON.parse(localStorage.getItem("Cotizacion")) as QuotationDetails;
    this.subtotal = this.cotizacion.seleccionProvedores
    .map(c => c.costoPersona)
    .reduce(
        (sum: number, values) => sum + values,
        0
    );
    this.subtotal= this.subtotal*this.cotizacion.numPersonas;
    this.iva= this.subtotal*0.19;
    this.total= this.subtotal+this.iva;
  }


  cotizar(){
    this.quotation.fechaCotizacion = this.cotizacion.fechaCotizacion;
    this.quotation.fechaInicio = new Date(this.cotizacion.fechaInicio).valueOf();
    this.quotation.fechaFin = new Date(this.cotizacion.fechaFin).valueOf();
    this.quotation.idActividad = this.cotizacion.seleccionProvedores[0].id;
    this.quotation.idHospedaje = this.cotizacion.seleccionProvedores[1].id;
    this.quotation.idTransporte = this.cotizacion.seleccionProvedores[2].id;
    this.quotation.numPersonas= this.cotizacion.numPersonas;
    this.cartService.insertRunTicket(this.quotation).subscribe(
      result => {
      this.showSuccess();
      },
      err => {

      }
    );
  }

  open(content) {
    this.modalService.open(content);
}

  showSuccess() {
    
    this.toastService.show("Se ha generado una cotización", { classname: 'bg-success text-light', delay: 15000 });
    this.router.navigate(["/checkout"]);
    this.modalService.dismissAll();
  }

}
