import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {AuthorizationService} from '../authorization/authorization.service';

@Injectable()
export class AuthorizationHttp {

  applicationUrl = 'https://localhost:8800';

  constructor(private http: Http, private authotrizationService: AuthorizationService) {}

  createAuthorizationHeader(headers: Headers) {
    headers.append('Authorization', 'Bearer ' + this.authotrizationService.token);
  }

  get(url) {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.get(this.applicationUrl + url, {
      headers: headers
    });
  }

  post(url, data) {
    const headers = new Headers();
    this.createAuthorizationHeader(headers);
    return this.http.post(this.applicationUrl + url, data, {
      headers: headers
    });
  }
}
