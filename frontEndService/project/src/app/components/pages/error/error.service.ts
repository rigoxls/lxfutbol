import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import { OrderInterface } from '../../interfaces/orders.interface';

@Injectable({
    providedIn: 'root',
})
export class ErrorService {
    
    private url = environment.userServiceUrl + 'orders/createOrder';

    constructor(private http: HttpClient) {
    }
    
    public createOrder(order ?: OrderInterface) {
        const headers = {
            'Content-Type': 'application/json',
        };
        return this.http.post(this.url, order, {headers});
    }

}
