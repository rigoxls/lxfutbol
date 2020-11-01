import { Transporte } from './transporte.interface';
import { Hospedaje } from './hospedaje.interface';

export interface ActivityDetails {
    nombreSitioTuristico: string;
    nombreMunicipio: string;
    nombreDepartamento: string;
    idActividad: number;
    nombreActividad: string;
    estado: number;
    latitud: string;
    longitud: string;
    imagenesActividad: Array<string>;
    provedoresTransporte: Array<Transporte>;
    provedoresHospedaje: Array<Hospedaje>;
    precioBase: number;
    review: number;
}
