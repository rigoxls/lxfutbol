import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import {UserComponent} from './components/pages/user/user.component';
import {UserListComponent} from './components/pages/user-list/user-list.component';
import {ProviderComponent} from './components/pages/provider/provider.component';
import {ProviderListComponent} from './components/pages/provider-list/provider-list.component';
import {OrdersComponent} from './components/pages/orders/orders.component';
import {DetailOrderComponent} from './components/pages/detail-order/detail-order.component';

const routes: Routes = [
    {path: '', component: DashboardComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'user', component: UserComponent},
    {path: 'user-edit/:id', component: UserComponent},
    {path: 'user-list', component: UserListComponent},
    {path: 'provider', component: ProviderComponent},
    {path: 'provider-edit/:id', component: ProviderComponent},
    {path: 'provider-list', component: ProviderListComponent},
    {path: 'provider-list/:type', component: ProviderListComponent},
    {path: 'orders', component: OrdersComponent},
    {path: 'detailOrder', component: DetailOrderComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
