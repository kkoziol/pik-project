import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {AuthorizationService} from "../authorization/authorization.service";

@Injectable()
export class AuthorizationHttp {

  applicationUrl : string = 'https://192.168.1.72:8800';

  constructor(private http: Http, private authotrizationService: AuthorizationService) {}

  createAuthorizationHeader(headers: Headers) {
    headers.append('Authorization', this.authotrizationService.token)
  }

  get(url) {
    let headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.get(this.applicationUrl + url, {
      headers: headers
    });
  }

  post(url, data) {
    let headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.post(this.applicationUrl + url, data, {
      headers: headers
    });
  }
}
