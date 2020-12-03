import { ItemsInterface } from "./items.interface";

export interface OrderInterface {
    nameUser: string;
    lastNameUser: string;
    numDocumentUser: string;
    totalValue: number;
    email: string;
    address: string;
    paidStatus: string;
    items: Array<ItemsInterface>;
    phone: String;
}
