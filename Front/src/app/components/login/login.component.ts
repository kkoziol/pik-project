import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from '../../services/authorization/authorization.service';
import {Router} from '@angular/router';
import {EmailValidator} from "../../controlers/EmailValidator";


@Component({
  selector: 'my-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.scss' ]
})
export class LoginComponent implements OnInit {

  password: string;
  username: string;
  register: boolean;
  success: boolean;
  error: boolean;
  error2: boolean;

  firstName: string;
  lastName: string;
  eMail: string;
  gender: string;
  birthDate: string;
  confirmPassword: string;

  emailValidate: boolean;
  passwordValidate: boolean;

  constructor(private authorizationService: AuthorizationService, private router: Router) {
    this.password = '';
    this.username = '';
    this.register = false;
    this.success = false;
    this.error = false;
    this.error2 = false;
    this.firstName = '';
    this.lastName = '';
    this.eMail = '';
    this.gender = '';
    this.birthDate = '';
    this.confirmPassword = '';

    this.emailValidate = true;
    this.passwordValidate = true;
  }

  ngOnInit(): void {
  }

  loginUser() {
    this.authorizationService.login(this.username, this.password);
  }

  registerUser() {
   if (this.username !== '' && this.password !== '' && this.firstName !== '' && this.lastName !== '' && this.eMail !== '' && this.gender !== '' && this.confirmPassword !== '') {
    this.error2 = false;
    if (this.authorizationService.register(this.username, this.password, this.firstName, this.lastName, this.eMail, this.gender, this.birthDate, this.confirmPassword)) {
      this.success = true;
       this.password = '';
       this.username = '';
       this.firstName = '';
       this.lastName = '';
       this.eMail = '';
       this.gender = '';
       this.confirmPassword = '';
      setTimeout((router: Router) => {
        this.register = false;
    }, 3000);
    }else {
      this.error = true;
    }
    }else {
     this.error2 = true;
   }
  }

  validateEmail() {
     this.emailValidate = EmailValidator.mailValidate(this.eMail);
     console.log(this.emailValidate);
    console.log('Email validation');
  }

  validatePassword() {
    this.passwordValidate = (this.password === this.confirmPassword);
    console.log(this.emailValidate);
  }


}
