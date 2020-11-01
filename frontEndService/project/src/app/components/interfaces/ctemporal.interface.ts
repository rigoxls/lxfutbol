import { ProviderSelection } from './providerSelection..interface';

export class QuotationDetails {
    numPersonas: number;
    seleccionProvedores: Array<ProviderSelection>;
    nombre: string;
    email: string;
    fechaCotizacion: Date;
    fechaInicio: Date;
    fechaFin: Date;
}
