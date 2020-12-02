import {Injectable} from '@angular/core';
import {Spectactle} from '../../interfaces/spectacle.interface';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class HomeService {

    private url = environment.integratorServiceUrl + 'integrator/spectacle';

    constructor(private http: HttpClient) {
    }

    getSpectacle(param?: string): Promise<Spectactle[]> {
        return new Promise((resolve, reject) => {
            const url = (param) ? `${this.url}/${param}` : `${this.url}`;
            const headers = {
                'Content-Type': 'application/json'
            };

            const params = {
                data: {
                    operation: 'search',
                    city: '',
                    country: '',
                    type: ' ',
                    date: '',
                    dateEnd: ''
                }
            };

            this.http.post<Spectactle[]>(url, params,
                {headers}).subscribe(result => {
                    let idGen = 0;
                    const response = [];
                    // @ts-ignore
                    result = result.sort((a, b) => {
                        return a.agreement > b.agreement;
                    });

                    result.forEach(prov => {
                        for (const p of prov['providerEspectacle']) {
                            idGen = idGen + 1 ;
                            p['id'] = idGen;
                            response.push(p);
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
