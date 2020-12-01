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
    let quotation:any = JSON.parse('{"name":"","email":" ","quotationDate":"2020-11-29","selectProviders":[{"type":1,"numPeople":3,"name":"Partido Ecuador vs Colombia","price":165000},{"type":2,"numPeople":3,"name":"Hotel Carton, Luxury suits","price":360000,"startDate":"2020-2-12","endDate":"2020-2-15"},{"type":3,"numPeople":"4","name":"American Airlines","price":400000,"startDate":"2020-2-12","endDate":"2020-2-15"}]}');
    this.cotizacion= quotation as QuotationDetails;
    this.subtotal = this.cotizacion.selectProviders
    .map(c => c.price)
    .reduce(
        (sum: number, values) => sum + values,
        0
    );
    this.subtotal= this.subtotal*this.cotizacion.selectProviders[0].numPeople;
    this.iva= this.subtotal*0.19;
    this.total= this.subtotal+this.iva;
    localStorage.setItem("Total",JSON.stringify(this.total))
  }


  cotizar(){
    this.quotation.quotationDate = new Date();
    this.quotation.startDate = new Date(this.cotizacion.selectProviders[1].startDate);
    this.quotation.endDate = new Date(this.cotizacion.selectProviders[1].endDate);
    this.quotation.idSpectacle = this.cotizacion.selectProviders[0].type;
    this.quotation.idLodging = this.cotizacion.selectProviders[1].type;
    this.quotation.idTransport = this.cotizacion.selectProviders[2].type;
    this.quotation.numPeople= this.cotizacion.selectProviders[0].numPeople;
    this.cartService.insertQuotation(this.quotation).subscribe(
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
    
    //this.toastService.show("Se ha generado una cotización", { classname: 'bg-success text-light', delay: 15000 });
    this.router.navigate(["/checkout"]);
    this.modalService.dismissAll();
  }

}
