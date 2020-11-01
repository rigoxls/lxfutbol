import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivityDetails } from '../../interfaces/activityDetails.interface';
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ListingDetailsService {

  private url = environment.APIEndPoint+"/informacionActividad"

  constructor(private http: HttpClient) { }

  getinfoBySiteId2(id:number) : any{

    return [
        {
          nombreSitioTuristico: 'Salento',
          nombreDepartamento: 'Quindio',
          latitud:'4.633',
          longitud:'-75.567',
          descripcion: 'En Paramo Trek, te animamos a hacer un viaje fuera de lo normal, a abrazar lo inesperado y sumergirse en lo extraordinario. Nuestros treks te permiten vivir lo auténtico de nuestra región, con su cultura fascinante, sus costumbres, alojamientos, transporte local y vida silvestre. Una combinación perfecta que hará que siempre guardes estos recuerdos en tu corazón. Creemos firmemente que un equipo profesional y apasionado por su rol de servicio es el factor más importante en la calidad del resultado. Nos complacemos en hacer de este viaje, el Trek De Tu Vida! Routes: Cima Nevado del Tolima, Santa Isabel, Paramillo del Quindio. Borde de Glaciar Nevado del Tolima, rutas Salento - Pereira, Salento - Ibague',
          precioBase: 25000,
          idActividad: 1,
          nombreActividad:'Salento Trakking Tour',
          descripcionActividad: 'En Paramo Trek, te animamos a hacer un viaje fuera de lo normal, a abrazar lo inesperado y sumergirse en lo extraordinario. Nuestros treks te permiten vivir lo auténtico de nuestra región, con su cultura fascinante, sus costumbres, alojamientos, transporte local y vida silvestre. Una combinación perfecta que hará que siempre guardes estos recuerdos en tu corazón. Creemos firmemente que un equipo profesional y apasionado por su rol de servicio es el factor más importante en la calidad del resultado. Nos complacemos en hacer de este viaje, el Trek De Tu Vida! Routes: Cima Nevado del Tolima, Santa Isabel, Paramillo del Quindio. Borde de Glaciar Nevado del Tolima, rutas Salento - Pereira, Salento - Ibague',
          review: 4,
          imagenesActividad: [
            'assets/img/trekking.jpg',
            'assets/img/trekking5.png'
        ],
          estado: 1,
          provedoresTransporte: [
            {idTransportadora:1 ,costoPersona: 40000, tipo: 'terrestre', transportadora:'Expreso Gacela'},
            {idTransportadora:2, costoPersona: 60000, tipo: 'terrestre', transportadora:'Buses nova'},
            {idTransportadora:3, costoPersona: 44000, tipo: 'terrestre', transportadora:'Supertaxis'},
          ],
          provedoresHospedaje: [
            {idHospedaje:1 ,costoPersona: 40000, tipo: 'Glamping', nombre:'Hotel 1'},
            {idHospedaje:2, costoPersona: 60000, tipo: 'Cabañas', nombre:'Hotel 2'},
            {idHospedaje:3, costoPersona: 44000, tipo: 'Hotel', nombre:'Hotel 3'},
          ]

        }
    ]
  }
  getActivities(id:number) {
    let params = new HttpParams().set('idActividad',id.toString())
    const headers = {
                'Content-Type': 'application/json'
            };

  return this.http
    .get(this.url, {params,headers});
  }


}
