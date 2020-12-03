import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import { Email } from '../../interfaces/email.interface';
import { OrderInterface } from '../../interfaces/orders.interface';

@Injectable({
    providedIn: 'root',
})
export class SuccessService {
    
    private url = environment.userServiceUrl;
    private email: Email = new Email();

    constructor(private http: HttpClient) {
    }

    public createOrder(order ?: OrderInterface) {
        const vUrl = `${this.url}/orders/createOrde`;
        const headers = {
            'Content-Type': 'application/json',
        };
        return this.http.post(vUrl, order, {headers});
    }


    sendEmail() {
        const vUrl = `${this.url}/email/send`;
        const headers = {
            'Content-Type': 'application/json',
        };
        this.email.email = "rodrigo2bastidas_@hotmail.com"
        this.email.content = ``;
        this.email.subject = 'Resumen de su compra';
        return this.http.post(vUrl, this.email, {headers});

    }

}
