import { Injectable } from "@angular/core";
import { Quotation } from "../../interfaces/quotation.interface";
import { Email } from "../../interfaces/email.interface";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: "root",
})
export class CartService {

    private url = environment.APIEndPoint;

    constructor(private http: HttpClient) {}

    public insertRunTicket(pTicket: Quotation) {
        const headers = {
            "Content-Type": "application/json",
        };
        const vUrl = `${this.url}/crear/cotizacion`;
        return this.http.post(vUrl, pTicket, { headers });
    }

    public sendEmail(email: Email) {
      const headers = {
          "Content-Type": "application/json",
      };
      const vUrl = `${this.url}/email/send`;
      return this.http.post(vUrl, email, { headers });
  }
}
