import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import { OrderInterface } from '../../interfaces/orders.interface';
import {environment} from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class OrdersService {

  private url = environment.APIEndPoint + 'orders/findAll';

  constructor(private http: HttpClient) {

  }

  public findAll(){
      const headers = {
          "Content-Type": "application/json",
      };
      return this.http.get<OrderInterface[]>(this.url, {headers});

  }
}
