export interface UserInterface {
    tipoUsuario: number;
    nombres: string;
    apellidos: string;
    identificacion: string;
    email: string;
    password: string;
    telefono: string;
    idRole: number;
    nombreProveedor: string;
    nombreRepresentante: string;
    idUsuario?: number;
    idProveedor?: number;
}
