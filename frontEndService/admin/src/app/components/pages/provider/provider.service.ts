import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ProviderInterface} from '../../interfaces/provider.interface';
import {environment} from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ProviderService {

    private baseUrl = environment.APIEndPoint;

    constructor(private http: HttpClient) {
    }

    getProvidertById(providerId?: number): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}/provider/${providerId}`,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

    upsertProvider(provider: ProviderInterface, providerId?: number) {
        return new Promise((resolve, reject) => {
            const providerSave = provider;
            const method = (providerId) ? 'put' : 'post';

            const headers = {
                'Content-Type': 'application/json'
            };

            const logguedUser = JSON.parse(localStorage.getItem('logguedUser'));

            const formData = {
                nit: providerSave.nit,
                name: providerSave.name,
                representative: providerSave.representative,
                phone: providerSave.phone + ', ' + providerSave.phone2,
                email: providerSave.email,
                address: providerSave.address,
                type: providerSave.type,
                search: providerSave.searchContract,
                book: providerSave.bookContract,
                cancelBook: providerSave.cancelContract,
                status: providerSave.status,
                dataType: providerSave.dataType,
                agreement: providerSave.agreement
            };

            if (providerId) {
                formData['id'] = providerId;
            }

            return this.http[`${method}`]<any>(`${this.baseUrl}/provider`, formData, {headers}).subscribe(response => {
                    resolve({status: 201});
                },
                error => {
                    reject(error);
                });
        });
    }
}
