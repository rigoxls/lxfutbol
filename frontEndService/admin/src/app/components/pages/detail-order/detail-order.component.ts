import { Component, OnInit } from '@angular/core';
import { OrderInterface } from '../../interfaces/orders.interface';

@Component({
  selector: 'app-detail-order',
  templateUrl: './detail-order.component.html',
  styleUrls: ['./detail-order.component.scss']
})
export class DetailOrderComponent implements OnInit {

  orderSelected: OrderInterface;

  constructor() { }

  ngOnInit(): void {
    this.orderSelected = JSON.parse(localStorage.getItem('order'));
  }

}
