import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {Registry} from '../../interfaces/registry';

@Injectable({
    providedIn: 'root',
})
export class RegisterService {
    private url = environment.userServiceUrl + 'user/registry';
    constructor(private http: HttpClient) {
    }

    public registryUser(userRegistry?: Registry) {
        const headers = {
            'Content-Type': 'application/json',
        };
        return this.http.post(this.url, userRegistry, {headers});
    }

}
