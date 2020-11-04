import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Usuario} from '../../interfaces/usuario.interface';
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    private url = environment.APIEndPoint+'getAutenticacion';


    constructor(private http: HttpClient) {
    }

    public getAtutenticar(email?: string, password?: string){
        let params = new HttpParams().set('email', email).set('passWord', password)
        const headers = {
            "Content-Type": "application/json",
        };
        return this.http.get<Usuario>(this.url, {params, headers});

    }
}

