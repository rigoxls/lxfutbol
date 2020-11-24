import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Transport} from '../../interfaces/transport.interface';

@Injectable({
    providedIn: 'root'
})
export class TransportService {

    private url = environment.APIEndPoint + 'integrator/transport';

    constructor(private http: HttpClient) {
    }

    getTransports(sParams?: { fromDate: string, toDate: string }): Promise<Transport[]> {
        return new Promise((resolve, reject) => {

            const spectacleBooked = JSON.parse(localStorage.getItem('spectacleBook'));
            const initDate = spectacleBooked.date.split('-');
            const params = {
                operation: 'search',
                departureCity: 'Bogota',
                arrivalCity: spectacleBooked.city,
                country: 'Colombia',
                checkIn: (sParams) ? sParams.fromDate : spectacleBooked.date,
                checkOut: (sParams) ? sParams.toDate : `${initDate[0]}-${initDate[1]}-${parseInt(initDate[2], 10) + 3}`
            };

            const url = `${this.url}/${params.departureCity}/${params.arrivalCity}/${params.checkIn}`;
            const headers = {
                'Content-Type': 'application/json'
            };

            this.http.get<Transport[]>(url,
                {headers}).subscribe(result => {

                    let idGen = 0;
                    const response = [];
                    // @ts-ignore
                    result = result.sort((a, b) => {
                        return a.agreement > b.agreement;
                    });

                    result.forEach(prov => {
                        if (prov['providerTransport']) {
                            for (const p of prov['providerTransport']) {
                                idGen = idGen + 1;
                                p['id'] = idGen;
                                p['name'] = prov.name;
                                response.push(p);
                            }
                        }
                    });

                    localStorage.setItem('transports', JSON.stringify(response));
                    resolve(response);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }
}
