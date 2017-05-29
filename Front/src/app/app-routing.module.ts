import {NgModule}             from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LoginComponent}       from "./components/login/login.component";
import {OrdersComponent}      from "./components/orders/orders.component";
import {EBayComponent}        from "./components/eBay/eBay.component";
import {HistoryComponent}     from "./components/history/history.component";
import {SettingsComponent}    from "./components/settings/settings.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'orders', component: OrdersComponent},
  {path: 'eBay', component: EBayComponent},
  {path: 'history', component: HistoryComponent},
  {path: 'settings',component: SettingsComponent },
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
