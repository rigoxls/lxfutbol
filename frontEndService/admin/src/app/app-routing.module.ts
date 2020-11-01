import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './components/pages/dashboard/dashboard.component';
import {AddActivityComponent} from './components/pages/activity/add-activity.component';
import {ListActivityComponent} from './components/pages/activity-list/list-activity.component';
import {UserComponent} from './components/pages/user/user.component';
import {UserListComponent} from './components/pages/user-list/user-list.component';
import {TransportComponent} from './components/pages/transport/transport.component';
import {TransportListComponent} from './components/pages/transport-list/transport-list.component';
import { LodgingComponent } from './components/pages/lodging/lodging/lodging.component';
import { LodgingListComponent } from './components/pages/lodging-list/lodging-list.component';

const routes: Routes = [
    {path: '', component: DashboardComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'activity', component: AddActivityComponent},
    {path: 'activity-edit/:id', component: AddActivityComponent},
    {path: 'activity-list', component: ListActivityComponent},
    {path: 'user', component: UserComponent},
    {path: 'user-edit/:id', component: UserComponent},
    {path: 'user-list', component: UserListComponent},
    {path: 'transport', component: TransportComponent},
    {path: 'transport-edit/:id', component: TransportComponent},
    {path: 'transport-list', component: TransportListComponent},
    {path: 'lodging', component: LodgingComponent},
    {path: 'lodging-edit/:id', component: LodgingComponent},
    {path: 'lodging-list', component: LodgingListComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
