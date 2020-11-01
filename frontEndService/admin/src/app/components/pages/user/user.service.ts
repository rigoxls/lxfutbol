import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserInterface} from '../../interfaces/user.interface';
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

    private baseUrl = environment.APIEndPoint;

    constructor(
        private http: HttpClient,
    ) {
    }

    getUserById(userId?: number): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}user/get/${userId}`,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

    upsertActivity(user: UserInterface, userId?: number) {
        return new Promise((resolve, reject) => {
            const userSave = user;
            const path = (userId) ? 'user/update' : 'user/create';

            const headers = {
                'Content-Type': 'application/json'
            };

            const formData = {
                tipoUsuario: parseInt(String(userSave.tipoUsuario), 10),
                nombres: userSave.nombres,
                apellidos: userSave.apellidos,
                identificacion: userSave.identificacion,
                email: userSave.email,
                password: userSave.password,
                telefono: userSave.telefono,
                idRole: parseInt(String(userSave.tipoUsuario), 10),
                nombreProveedor: userSave.nombreProveedor,
                nombreRepresentante: userSave.nombreRepresentante,
            };

            if (userId) {
                formData['idUsuario'] = userId;
                formData['idProveedor'] = userSave.idProveedor;
            }

            return this.http.post<any>(`${this.baseUrl}${path}`, formData, {headers}).subscribe(response => {
                    resolve({status: 201});
                },
                error => {
                    reject(error);
                });
        });
    }
}
