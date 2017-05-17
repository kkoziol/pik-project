import { Injectable } from '@angular/core';
import {Authorize} from "./authorization.model";


@Injectable()
export class AuthorizationService {
  authorize:Authorize;

  constructor(){
    this.authorize = {active : false};
  }

  login(){
    this.authorize.active = true;
  }
}
