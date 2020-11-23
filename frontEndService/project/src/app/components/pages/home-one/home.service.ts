import {Injectable} from '@angular/core';
import {Activity} from '../../interfaces/spectacle.interface';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class HomeService {

    private url = environment.APIEndPoint + 'integrator/spectacle';

    constructor(private http: HttpClient) {
    }

    getSpectacle(param?: string): Promise<Activity[]> {
        return new Promise((resolve, reject) => {
            const url = (param) ? `${this.url}/${param}` : `${this.url}`;
            const headers = {
                'Content-Type': 'application/json'
            };

            const params = {
                data: {
                    operation: 'search',
                    city: 'Cartagena',
                    country: 'Colombia',
                    type: 'futbol ',
                    date: '2020-12-15',
                    dateEnd: '2020-12-15'
                }
            };

            this.http.post<Activity[]>(url, params,
                {headers}).subscribe(result => {
                    const response = [];
                    debugger;
                    result.forEach(prov => {
                        if (Array.isArray(prov)) {

                        } else {
                            response.push(prov['providerEspectacle']);
                        }
                    });

                    resolve(response);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

}
