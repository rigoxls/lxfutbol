import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private url = 'http://localhost:53472/api/PaymentController';

  constructor(private http: HttpClient) { }

  payService(){
    const headers = {
      "Content-Type": "application/json"
  };
   const vUrl = `${this.url}/create-checkout-session`;
   return this.http.post(vUrl, "", { headers });

  }
}
