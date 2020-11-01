import {Component, OnInit} from '@angular/core';
import {Router, NavigationStart, NavigationCancel, NavigationEnd} from '@angular/router';
import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
import {filter} from 'rxjs/operators';
import {AppServiceService} from './app-service.service';
import { CONFIG } from 'src/assets/config';

declare let $: any;

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    providers: [
        Location, {
            provide: LocationStrategy,
            useClass: PathLocationStrategy
        }
    ]
})
export class AppComponent implements OnInit {
    location: any;
    routerSubscription: any;
    private loginUrl = CONFIG.userLoggedPath+"login";

    constructor(
        private router: Router,
        private appServiceService: AppServiceService) {
    }

    ngOnInit() {
        this.recallJsFuntions();
        this.appServiceService.getSession().then( result => {
        }).catch( error => {
            window.location.href = this.loginUrl;
        });
    }

    recallJsFuntions() {
        this.routerSubscription = this.router.events
            .pipe(filter(event => event instanceof NavigationEnd || event instanceof NavigationCancel))
            .subscribe(event => {
                $.getScript('../assets/js/custom.js');
                this.location = this.router.url;
                if (!(event instanceof NavigationEnd)) {
                    return;
                }
                window.scrollTo(0, 0);
            });
    }
}
