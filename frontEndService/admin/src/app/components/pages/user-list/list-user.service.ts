import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ListUserService {

    private baseUrl = environment.APIEndPoint;

    constructor(
        private http: HttpClient,
    ) {
    }

    getUsers(): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}user/list`,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

    deleteUser(userId: number) {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.delete<any[]>(`${this.baseUrl}user/delete/${userId}`,
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
