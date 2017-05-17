import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";

@Component({
  selector: 'my-eBay',
  templateUrl: './eBay.component.html',
  styleUrls: [ './eBay.component.scss' ]
})


export class EBayComponent implements OnInit {

  constructor(private autorizationService: AuthorizationService) { }

  ngOnInit(): void {
  }
}
