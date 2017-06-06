import {NgModule}             from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LoginComponent}       from "./components/login/login.component";
import {OrdersComponent}      from "./components/orders/orders.component";
import {EBayComponent}        from "./components/eBay/eBay.component";
import {ResultComponent} from "./components/results/result.component";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'orders', component: OrdersComponent},
  {path: 'eBay', component: EBayComponent},
  {path: 'login', component: LoginComponent},
  {path: 'result', component: ResultComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
