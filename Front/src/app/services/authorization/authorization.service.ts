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
    const headers = new Headers({ 'Accept' : 'application/json',
                                  'Authorization': 'Basic' + encoded,
                                  'Content-Type' : 'application/x-www-from-urlencoded'});
    const options = new RequestOptions({ headers: headers });

    console.log(url);

    this.http.post(url, JSON.stringify(data), options)
        .map(res =>  res.json().data)
        .subscribe(token => this.token = token);
    window.sessionStorage.setItem('token',this.token)
  }



}
