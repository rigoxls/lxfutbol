import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../../../app-service.service';
import { CONFIG } from 'src/assets/config';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    public email = null;
    public userName = null;

    public loginUrl = CONFIG.userLoggedPath+"login";

    constructor(private appServiceService: AppServiceService) {
    }

    ngOnInit(): void {
        this.appServiceService.sessionEmitter.subscribe(session => {
            if (session) {
                session = JSON.parse(session);
                this.email = session['emailUsuario'];
                this.userName = session['nombreUsuario'] + ' ' + session['apellidosUsuario'];
                if (session.roles.id_rol === 3) {

                } else {

                }
            }
        });
    }

    logOut() {
        this.appServiceService.logOut().then(result => {
            window.location.href = this.loginUrl;
        });
    }

}
