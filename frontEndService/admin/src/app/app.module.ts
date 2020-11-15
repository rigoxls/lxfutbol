import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FooterComponent} from './components/layouts/footer/footer.component';
import {HeaderComponent} from './components/layouts/header/header.component';
import {SidebarComponent} from './components/layouts/sidebar/sidebar.component';
import {DashboardComponent} from './components/pages/dashboard/dashboard.component';
import {WebAnalyticsComponent} from './components/charts/web-analytics/web-analytics.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { UserComponent } from './components/pages/user/user.component';
import { UserListComponent } from './components/pages/user-list/user-list.component';
import { ProviderComponent } from './components/pages/provider/provider.component';
import { TransportListComponent } from './components/pages/transport-list/transport-list.component';

@NgModule({
    declarations: [
        AppComponent,
        FooterComponent,
        HeaderComponent,
        SidebarComponent,
        DashboardComponent,
        WebAnalyticsComponent,
        UserComponent,
        UserListComponent,
        ProviderComponent,
        TransportListComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
