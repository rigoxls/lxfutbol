import { OrderInterface } from '../../interfaces/orders.interface';
import { OrdersService } from './orders.service';
import { Component, OnInit } from '@angular/core';
import { TableModule } from 'primeng/table';



@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit {

  orders: OrderInterface[] = [];

  constructor(private orderServices: OrdersService) { }

  ngOnInit(): void {

    this.orderServices.findAll().subscribe(
      result => {
        this.orders = result;
        localStorage.setItem('orders', JSON.stringify(this.orders));
      },
      err => {

      }
    );

  }


  setOrder(orderSelected: OrderInterface) {
    localStorage.setItem('order', JSON.stringify(orderSelected));
  }

}
