import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";
//import {EmailValidator} from "../../controlers/EmailValidator";


@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.scss' ]
})
export class LoginComponent implements OnInit{

  password: string;
  username: string;
  register: boolean;

  firstName: string;
  lastName: string;
  eMail: string;
  gender: string;
  birthDate: string;
  confirmPassword: string;

  emailValidate: boolean;
  passwordValidate: boolean;

  constructor(private authorizationService: AuthorizationService) {
    this.password = "";
    this.username = "";
    this.register = false;
    this.firstName = "";
    this.lastName = "";
    this.eMail = "";
    this.gender = "";
    this.birthDate = "";
    this.confirmPassword = "";

    this.emailValidate = true;
    this.passwordValidate = true;
  }

  ngOnInit(): void {
  }

  loginUser(){
    this.authorizationService.login(this.username,this.password);
  }

  registerUser(){
    this.authorizationService.register(this.username,this.password,this.firstName, this.lastName,this.eMail,this.gender,this.birthDate,this.confirmPassword);
  }

  validateEmail() {
   // this.emailValidate = EmailValidator.mailValidate(this.eMail);
   // console.log(this.emailValidate);
    console.log("Email validation");
  }

  validatePassword() {
    this.passwordValidate = (this.password === this.confirmPassword);
    console.log(this.emailValidate);
  }


}
