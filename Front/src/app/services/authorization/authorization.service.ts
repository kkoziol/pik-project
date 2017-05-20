import { Injectable } from '@angular/core';
import {Authorize} from "./authorization.model";
import {Observable} from "rxjs/Observable";
import {Headers,Http, RequestOptions} from "@angular/http";


@Injectable()
export class AuthorizationService {
  authorize:Authorize;
  token:string;

  constructor(private http:Http){
    this.authorize = {active : true};
    this.login();
  }

  login(){
    const url = 'https://192.168.1.72:8800/oauth/token';
    const data = { grant_type: 'password',
                    username : 'test2',
                    password : 'test2'};
    const encoded = btoa("pik-webapp-client:secret");
    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + encoded);
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    const options = new RequestOptions({ headers: headers });

    console.log('Basic ' + encoded);

    this.http.post(url, JSON.stringify(data), options)
        .map(res =>  res.json().data)
        .subscribe(token => this.token = token);

    this.token = "50ec0e5c-67ff-4dc8-90e1-d7a6364b2c5";
    window.sessionStorage.setItem('token',this.token)
  }



}
