import { RolUsuario } from "./rolusuario.interface";

export interface Usuario {
    id_usuario: number;
    identificacionUsuario: string;
    nombreUsuario: string;
    apellidosUsuario: string;
    telefonoUsuario: string;
    emailUsuario: string;
    roles: Array<RolUsuario>;
    password: string;
 }
