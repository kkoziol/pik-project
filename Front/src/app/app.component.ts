import { Component } from '@angular/core';
import {AuthorizationService} from "./services/authorization/authorization.service";
import {Authorize} from "./services/authorization/authorization.model";

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  logged:Authorize;

  constructor(private heroService: AuthorizationService)
  {
    this.logged = this.heroService.authorize;
  }


}
