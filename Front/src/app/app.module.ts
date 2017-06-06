import {NgModule}             from '@angular/core';
import {BrowserModule}        from '@angular/platform-browser';
import {FormsModule}          from '@angular/forms';

import {AppComponent}         from './app.component';

import {AppRoutingModule}     from './app-routing.module';
import {AuthorizationService} from "./services/authorization/authorization.service";
import {EBayComponent}        from "./components/eBay/eBay.component";
import {OrdersComponent}      from "./components/orders/orders.component";
import {LoginComponent}       from "./components/login/login.component";
import {AuthorizationHttp}    from "./services/authorizationHttp/authorizationHttp";
import {EBayService} from "./services/eBayApi/eBayApi.service";
import {HttpModule, JsonpModule} from "@angular/http";
import {FoundResultService} from "./services/foundResult/foundResult.service";
import {ResultComponent} from "./components/results/result.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    JsonpModule,
    HttpModule
  ],
  declarations: [
    AppComponent,
    EBayComponent,
    OrdersComponent,
    LoginComponent,
    ResultComponent
  ],
  providers: [
    FoundResultService,
    AuthorizationService,
    AuthorizationHttp
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
