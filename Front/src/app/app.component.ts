import { Component } from '@angular/core';
import {AuthorizationService} from "./services/authorization/authorization.service";
import {Authorize} from "./services/authorization/authorization.model";
import {Router} from "@angular/router";
import {FoundResultService} from "./services/foundResult/foundResult.service";

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  logged:Authorize;

  constructor(private authorizationService: AuthorizationService, private found: FoundResultService, private router: Router)
  {
    this.logged = this.authorizationService.authorize;
  }

  logout(){
    localStorage.clear();
    this.authorizationService.authorize.active = false;
    this.router.navigate(['./']);
    this.found.listening.active = false;
  }

}
