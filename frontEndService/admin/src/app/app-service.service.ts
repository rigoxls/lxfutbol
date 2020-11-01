import {Injectable, EventEmitter} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "./../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class AppServiceService {

    private baseUrl = environment.APIEndPoint;

    sessionEmitter = new EventEmitter<string>();

    constructor(private http: HttpClient) {
    }

    getSession() {
        return new Promise((resolve, reject) => {
            return this.http.get<any[]>(`${this.baseUrl}userLogged`,
                {}).subscribe(result => {
                    if (!result) {
                        reject(null);
                    }

                    const sessionUser = JSON.stringify(result);
                    this.sessionEmitter.emit(sessionUser);

                    localStorage.setItem('logguedUser', sessionUser);
                    resolve(result);

                },
                error => {
                    reject(null);
                });
        });
    }

    logOut() {
        return new Promise((resolve, reject) => {
            return this.http.get<any[]>(`${this.baseUrl}logout`,
                {}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    reject(null);
                });
        });
    }
}
