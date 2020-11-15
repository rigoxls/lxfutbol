import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ProviderService {

    private baseUrl = environment.APIEndPoint;

    constructor(
        private http: HttpClient,
    ) {
    }

    getProviders(param?: string): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}/provider/list/active`,
                {headers}).subscribe(result => {
                    //const logguedUser = JSON.parse(localStorage.getItem('logguedUser'));
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

    deleteProvider(providerId: number) {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.delete<any[]>(`${this.baseUrl}/provider/${providerId}`,
                {headers}).subscribe(result => {
                    resolve({status: 'ok'});
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }
}
