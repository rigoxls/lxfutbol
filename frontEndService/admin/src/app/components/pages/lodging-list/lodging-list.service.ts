import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LodgingListService {
 
  private baseUrl = environment.APIEndPoint;
  
  constructor(
    private http: HttpClient
  ) { }

  getlodgings(param?: string): any[] | Promise<any[]> {
    return new Promise((resolve, reject) => {
      const headers = {
          'Content-Type': 'application/json'
      };
      this.http.get<any[]>(`${this.baseUrl}lodging/list`,
          {headers}).subscribe(result => {
              const logguedUser = JSON.parse(localStorage.getItem('logguedUser'));
              result = result.filter(lodging => lodging.idUsuario === logguedUser.id_usuario);
              resolve(result);
          },
          error => {
              console.info(error);
              reject(error);
          });
  });
  }
  deleteLodging(lodgingId: any) {
    return new Promise((resolve, reject) => {
      const headers = {
          'Content-Type': 'application/json'
      };
      this.http.delete<any[]>(`${this.baseUrl}lodging/delete/${lodgingId}/hospedaje`,
          {headers}).subscribe(result => {
              resolve({status: 'ok'});
          },
          error => {
              console.info(error);
              reject(error);
          });
  });
  }
}
