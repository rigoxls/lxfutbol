import { RolUsuario } from "./rolusuario.interface";

export interface Usuario {
    id: number;
    idUser: string;
    name: string;
    lastName: string;
    phone: string;
    email: string;
    rol: Array<RolUsuario>;
    password: string;
 }
