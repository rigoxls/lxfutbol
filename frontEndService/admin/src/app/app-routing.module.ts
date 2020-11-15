import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import {UserComponent} from './components/pages/user/user.component';
import {UserListComponent} from './components/pages/user-list/user-list.component';
import {ProviderComponent} from './components/pages/provider/provider.component';
import {TransportListComponent} from './components/pages/transport-list/transport-list.component';

const routes: Routes = [
    {path: '', component: DashboardComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'user', component: UserComponent},
    {path: 'user-edit/:id', component: UserComponent},
    {path: 'user-list', component: UserListComponent},
    {path: 'transport', component: ProviderComponent},
    {path: 'provider-edit/:id', component: ProviderComponent},
    {path: 'provider-list', component: TransportListComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
