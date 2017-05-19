import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";


@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.scss' ]
})
export class LoginComponent implements OnInit{

  constructor(private heroService: AuthorizationService) {
  }

  ngOnInit(): void {
  }

  changeActive(){
    this.heroService.login();
  }


}
