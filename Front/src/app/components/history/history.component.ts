import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";
import {UserPreference} from "../orders/orders.model";
import {EBayService} from "../../services/eBayApi/eBayApi.service";
import {OrdersService} from "../../services/orders/orders.service";

@Component({
  selector: 'my-history',
  templateUrl: './history.component.html',
  styleUrls: [ './history.component.scss' ],
  providers: [OrdersService]
})


export class HistoryComponent implements OnInit {

  itemList: UserPreference[];

  constructor(private orderService: OrdersService) { }

  ngOnInit(): void {
    //this.orderService.getOrderByUser().subscribe(data => console.log(data));
  }
}
