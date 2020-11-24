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

    getTransports(param?: string): Promise<Transport[]> {
        return new Promise((resolve, reject) => {
            const params = {
                operation: 'search',
                departureCity: 'Cartagena',
                arrivalCity: 'Cartagena',
                country: 'Colombia',
                checkIn: '2020-12-02',
                checkOut: '2020-12-15',
                room: 2,
                type: 'Duplex'
            };

            const url = `${this.url}/${params.departureCity}/${params.arrivalCity}/${params.checkIn}`;
            const headers = {
                'Content-Type': 'application/json'
            };

            const spectacleBooked = JSON.parse(localStorage.getItem('spectacleBook'));


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
