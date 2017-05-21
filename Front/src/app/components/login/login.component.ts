import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";


@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.scss' ]
})
export class LoginComponent implements OnInit{

  password: string;
  username: string;

  constructor(private heroService: AuthorizationService) {
    this.password = "";
    this.username = "";
  }

  ngOnInit(): void {
  }

  loginUser(){
    this.heroService.login(this.username,this.password);
  }


}
