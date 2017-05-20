import { Injectable } from '@angular/core';
import {Authorize} from "./authorization.model";
import {Observable} from "rxjs/Observable";
import {Headers,Http, RequestOptions, URLSearchParams} from "@angular/http";


@Injectable()
export class AuthorizationService {
  authorize: Authorize;
  token: string;

  constructor(private http: Http) {
    this.authorize = {active : true};
    this.login();
  }

  login() {
    const url = 'https://localhost:8800/oauth/token';
   /* const data = { grant_type: 'password',
                    username : 'test2',
                    password : 'test2'};*/

    const paramsT: URLSearchParams = new URLSearchParams('grant_type=password&username=test2&password=test2');
//    paramsT.append('grant_type', 'password');
//    paramsT.append('username', 'test2');
//    paramsT.append('password', 'test2');
    const encoded = btoa('pik-webapp-client:secret');
    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + encoded);
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    const options = new RequestOptions({ headers: headers, params: paramsT});
    //options.params = params;
    console.log('Basic ' + encoded);

    this.http.post(url, paramsT.toString(), options)
        .map(res =>  res.json())
        .subscribe(access_token => this.token = access_token);

    /*this.token = "50ec0e5c-67ff-4dc8-90e1-d7a6364b2c5";*/
    console.log(this.token);
    window.sessionStorage.setItem('token', this.token);
  }



}
