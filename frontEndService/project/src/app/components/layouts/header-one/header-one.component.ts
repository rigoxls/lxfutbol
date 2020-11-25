import {Component, OnInit} from '@angular/core';
import {HeaderService} from './header.service';
import {Place} from '../../interfaces/place.interface';
import { Usuario } from '../../interfaces/usuario.interface';
import { Categorias } from '../../interfaces/categorias';

@Component({
    selector: 'app-header-one',
    templateUrl: './header-one.component.html',
    styleUrls: ['./header-one.component.scss']
})
export class HeaderOneComponent implements OnInit {

    places: Place[];
    categorias: String[];
    usuarioAtenticado:Usuario;
   
    constructor(
        private headerService: HeaderService) {
    }

    ngOnInit(): void {
        this.getCategorias();
        this.getPlaces();
        this.getUserAuthentication();
    }

    private async getPlaces(): Promise<Place[]> {
        this.places = await this.headerService.getPlaces();
        localStorage.setItem('places', JSON.stringify(this.places));
        return this.places;
    }    

    private async getUserAuthentication(): Promise<Usuario> {
        this.usuarioAtenticado = await this.headerService.findLoginUser();
        return this.usuarioAtenticado;
    }

    cerrarSesion(){
        localStorage.removeItem('userAutenticado');
        window.location.reload();
    }

    setNameCity(cityName : String){
        localStorage.setItem('city', JSON.stringify(cityName));
    }

    private async getCategorias(): Promise<String[]> {
        this.categorias = await this.headerService.finCategorys();
        return this.categorias;
    }

    setNameCategory(categoryName : String){
        localStorage.setItem('category', JSON.stringify(categoryName));
    }

}
