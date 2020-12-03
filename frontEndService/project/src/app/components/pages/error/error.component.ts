import { Component, OnInit } from '@angular/core';
import { ItemsInterface } from '../../interfaces/items.interface';
import { OrderInterface } from '../../interfaces/orders.interface';
import { Usuario } from '../../interfaces/usuario.interface';
import { ErrorService } from './error.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  orderPayment: any;
  order: OrderInterface = {} as any;
  itemsOrder: ItemsInterface[] = [];
  item: ItemsInterface = {} as any;
  constructor(private errorService: ErrorService) { }

  ngOnInit(): void {
    this.orderPayment = JSON.parse(localStorage.getItem('OrderPayment'));

    this.order.nameUser = this.orderPayment.name;
    this.order.lastNameUser = this.orderPayment.lastname;
    this.order.numDocumentUser = this.orderPayment.numDocumentUser;
    this.order.totalValue = this.orderPayment.totalOrder;
    this.order.email = this.orderPayment.email;
    this.order.address = this.orderPayment.address;
    this.order.paidStatus = 'Rechazado';
    this.order.phone = this.orderPayment.phone;

    for (var items in this.orderPayment.selectProviders) {
      this.item.name = this.orderPayment.selectProviders[items].name;
      this.item.value = this.orderPayment.selectProviders[items].price;
      this.item.quantity = this.orderPayment.selectProviders[items].numPeople;
      this.itemsOrder.push(this.item);
    }

    this.order.items = this.itemsOrder;
    console.log(JSON.stringify(this.order));
    this.errorService.createOrder(this.order);
  }

}
