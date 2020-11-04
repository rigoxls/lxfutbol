import {Injectable} from '@angular/core';
import {ActivityInterface} from '../../interfaces/activity.interface';
import {HttpClient} from '@angular/common/http';
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ActivityService {

    private baseUrl = environment.APIEndPoint;

    constructor(
        private http: HttpClient,
    ) {
    }

    getSites(): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}sitio/list`,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }


    getActivityById(activityId?: number): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const headers = {
                'Content-Type': 'application/json'
            };
            this.http.get<any[]>(`${this.baseUrl}activity/get/${activityId}`,
                {headers}).subscribe(result => {
                    resolve(result);
                },
                error => {
                    console.info(error);
                    reject(error);
                });
        });
    }

    upsertActivity(activity: ActivityInterface, activityId?: number, images?: Array<string>) {
        return new Promise((resolve, reject) => {
            const activitySave = activity;
            const path = (activityId) ? 'activity/update' : 'activity/create';

            const headers = {
                'Content-Type': 'application/json'
            };

            const formData = {
                categoria: activitySave.categoria,
                descripcion: activitySave.descripcion,
                estado: parseInt(String(activitySave.estado), 10),
                nombreActividad: activitySave.nombreActividad,
                precioBase: parseInt(activitySave.precioBase, 10),
                review: activitySave.review || 5,
                idSitio: activitySave.idSitio['id'],
                images
            };

            if (activityId) {
                formData['idActividad'] = activityId;
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
