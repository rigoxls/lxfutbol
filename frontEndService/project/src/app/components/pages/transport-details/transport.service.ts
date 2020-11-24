import { Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Lodge} from '../../interfaces/lodge.interface';

@Injectable({
  providedIn: 'root'
})
export class TransportService {

    private url = environment.APIEndPoint + 'integrator/lodge';

    constructor(private http: HttpClient) {
    }

    getLodges(param?: string): Promise<Lodge[]> {
        return new Promise((resolve, reject) => {
            const url = (param) ? `${this.url}/${param}` : `${this.url}`;
            const headers = {
                'Content-Type': 'application/json'
            };

            const spectacleBooked = JSON.parse(localStorage.getItem('spectacleBook'));

            const params = {
                data: {
                    operation: 'search',
                    city: 'Cartagena',
                    country: 'Colombia',
                    checkIn: '2020-12-02',
                    checkOut: '2020-12-15',
                    room: 2,
                    type: 'Duplex'
                }
            };

            this.http.post<Lodge[]>(url, params,
                {headers}).subscribe(result => {
                    let idGen = 0;
                    const response = [];
                    // @ts-ignore
                    result = result.sort((a, b) => {
                        return a.agreement > b.agreement;
                    });

                    result.forEach(prov => {
                        if (prov['providerLodge']) {
                            for (const p of prov['providerLodge']) {
                                idGen = idGen + 1;
                                p['id'] = idGen;
                                response.push(p);
                            }
                        }
                    });

                    localStorage.setItem('spectacles', JSON.stringify(response));
                    resolve(response);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }
}
