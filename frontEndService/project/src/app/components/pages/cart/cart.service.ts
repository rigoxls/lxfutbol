import { Injectable } from "@angular/core";
import { Quotation } from "../../interfaces/quotation.interface";
import { Email } from "../../interfaces/email.interface";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: "root",
})
export class CartService {

    private url = "http://localhost:5001/api/Quotations"

    constructor(private http: HttpClient) {}

    public insertQuotation(pQuotation: Quotation) {
        const headers = {
            "Content-Type": "application/json",
        };
        const vUrl = `${this.url}`;
        return this.http.post(vUrl, pQuotation, { headers });
    }

    public sendEmail(email: Email) {
      const headers = {
          "Content-Type": "application/json",
      };
      const vUrl = `${this.url}/email/send`;
      return this.http.post(vUrl, email, { headers });
  }
}
