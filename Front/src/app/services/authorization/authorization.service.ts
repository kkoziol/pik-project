import { Injectable } from '@angular/core';
import {Authorize} from './authorization.model';
import {Observable} from 'rxjs/Observable';
import {Headers, Http, RequestOptions, URLSearchParams} from '@angular/http';


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
    const paramsT: URLSearchParams = new URLSearchParams('grant_type=password&username=test2&password=test2');
    const encoded = btoa('pik-webapp-client:secret');
    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + encoded);
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    const options = new RequestOptions({ headers: headers, params: paramsT});
    console.log('Basic ' + encoded);

    let getData: any[];
    this.http.post(url, paramsT.toString(), options)
        .map(res =>  getData = res.json())
        .subscribe(getData => this.token = getData['access_token']);
    console.log(this.token);
    window.sessionStorage.setItem('token', this.token);
  }



}
