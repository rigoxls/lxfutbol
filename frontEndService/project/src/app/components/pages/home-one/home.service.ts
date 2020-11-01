import {Injectable} from '@angular/core';
import {Activity} from '../../interfaces/activity.interface';
import {HttpClient} from '@angular/common/http';
import { environment } from "src/environments/environment";
@Injectable({
    providedIn: 'root'
})
export class HomeService {

    private url = environment.APIEndPoint+'getActivities';

    constructor(private http: HttpClient) {
    }

    getActivities(param?: string): Promise<Activity[]> {
        return new Promise((resolve, reject) => {
            const url = (param) ? `${this.url}/${param}` : `${this.url}`;
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<Activity[]>(url,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

}
