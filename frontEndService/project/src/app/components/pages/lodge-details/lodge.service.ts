import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Lodge} from '../../interfaces/lodge.interface';
import {environment} from '../../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LodgeService {

    private url = environment.integratorServiceUrl + 'integrator/lodge';

    constructor(private http: HttpClient) {
    }

    getLodges(sParams?: { fromDate: string, toDate: string }): Promise<Lodge[]> {
        return new Promise((resolve, reject) => {
            const url = this.url;
            const headers = {
                'Content-Type': 'application/json'
            };

            const spectacleBooked = JSON.parse(localStorage.getItem('spectacleBook'));
            const initDate = spectacleBooked.date.split('-');

            const params = {
                data: {
                    operation: 'search',
                    city: spectacleBooked.city,
                    country: spectacleBooked.country,
                    checkIn: (sParams) ? sParams.fromDate : spectacleBooked.date,
                    checkOut: (sParams) ? sParams.toDate : `${initDate[0]}-${initDate[1]}-${parseInt(initDate[2], 10) + 3}`,
                    room: 1,
                    type: ''
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
