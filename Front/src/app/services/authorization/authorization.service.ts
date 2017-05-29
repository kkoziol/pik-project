import {Injectable} from '@angular/core';
import {Authorize} from "./authorization.model";
import {Headers, Http, RequestOptions, URLSearchParams} from "@angular/http";
import {Router} from "@angular/router";
import { AppConfigConsts} from "../../config";


@Injectable()
export class AuthorizationService {
  authorize: Authorize;
  token: string;
  username: string;

  constructor(private http: Http, private router: Router) {
    this.authorize = {active: false};
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));

    if (currentUser) {
      this.token = currentUser.token;
      this.username = JSON.parse(localStorage.getItem('currentUserName'));
      this.authorize.active = true;
    }
  }

  login(username: string, password: string) {
    const url = AppConfigConsts.serwerUrl + '/oauth/token';

    const paramsT: URLSearchParams = new URLSearchParams('grant_type=password&username=' + username + '&password=' + password);

    const encoded = btoa('pik-webapp-client:secret');

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + encoded);
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/x-www-form-urlencoded');

    const options = new RequestOptions({headers: headers, params: paramsT});

    this.http.post(url, undefined, options)
      .map(res => res.json())
      .subscribe(access_token => {
          this.token = access_token.access_token;
          this.username = username;
          this.authorize.active = true;
          this.router.navigate(['./eBay']);
          localStorage.setItem('currentUser', JSON.stringify({token: this.token}));
          localStorage.setItem('currentUserName', JSON.stringify({username: username}));
        },
        error2 => console.log("Zle haslo"));

  }

  register(username: string,password: string,firstName: string, lastName: string,eMail: string,gender: string,birthDate: string,confirmPassword: string) : boolean {
    const url ='https://localhost:8444/register';
    const userToAdd = {
      username: username,
      password: password,
      confirmPassword: confirmPassword,
      firstName: firstName,
      lastName: lastName,
      eMail: eMail,
      gender: gender,
      birthDate: birthDate
    };
    const headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    const options = new RequestOptions({headers: headers});
    this.http.post(url, JSON.stringify(userToAdd), options)
      .map(res => res.json())
      .subscribe(response => {
          //console.log(response.body);
        },
        error2 => {
          console.log("Wrong request");
          return false;
        });
    return true;
  }
}



