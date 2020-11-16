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
        this.getUsuarioAutenticado();
    }

    private async getPlaces(): Promise<Place[]> {
        this.places = await this.headerService.getPlaces();
        localStorage.setItem('places', JSON.stringify(this.places));
        return this.places;
    }    

    private async getUsuarioAutenticado(): Promise<Usuario> {
        this.usuarioAtenticado = await this.headerService.obtenerLoginUser();
        return this.usuarioAtenticado;
    }

    cerrarSesion(){
        localStorage.removeItem('userAutenticado');
        window.location.reload();
    }

    setNombreDestino(placesName : String){
        localStorage.setItem('placesName', JSON.stringify(placesName));
    }

    private async getCategorias(): Promise<String[]> {
        this.categorias = await this.headerService.getCategorias();
        return this.categorias;
    }

    setNombreCategoria(categoria : String){
        localStorage.setItem('categoria', JSON.stringify(categoria));
    }

}
