import {Component, OnInit} from '@angular/core';
import {AppServiceService} from '../../../app-service.service';
import { CONFIG } from 'src/assets/config';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    public role = null;

    private loginUrl = CONFIG.userLoggedPath+"login";

    constructor(private appServiceService: AppServiceService) {
    }

    ngOnInit(): void {
        this.appServiceService.sessionEmitter.subscribe(session => {
            if (session) {
                session = JSON.parse(session);
                this.role = session.roles.id_rol;
            }
        });
    }

    logOut() {
        this.appServiceService.logOut().then(result => {
            window.location.href = this.loginUrl;
        });
    }

}
