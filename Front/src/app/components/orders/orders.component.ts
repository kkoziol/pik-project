import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";

@Component({
  selector: 'my-orders',
  templateUrl: './orders.component.html',
  styleUrls: [ './orders.component.scss' ]
})


export class OrdersComponent implements OnInit {

  constructor(private autorizationService: AuthorizationService) { }

  ngOnInit(): void {
  }
}
