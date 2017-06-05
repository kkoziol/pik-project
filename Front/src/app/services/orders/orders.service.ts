import {Injectable} from "@angular/core";
import {AuthorizationHttp} from "../authorizationHttp/authorizationHttp";

@Injectable()
export class OrdersService {
  constructor(private authorizationHttp: AuthorizationHttp) {
  }

  putOrderPreferences(username: string, data: {}){
    return  this.authorizationHttp.post("/orders/add/"+username, data);
  }

  getUserOrders(username: string){
    return this.authorizationHttp.get("/orders/list/" + username);
  }

  deleteUserOrder(orderId: number){
    return this.authorizationHttp.get("/orders/delete/" + orderId);
  }
}

