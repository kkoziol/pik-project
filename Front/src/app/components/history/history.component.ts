import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";

@Component({
  selector: 'my-history',
  templateUrl: './history.component.html',
  styleUrls: [ './history.component.scss' ]
})


export class HistoryComponent implements OnInit {

  constructor(private autorizationService: AuthorizationService) { }

  ngOnInit(): void {
  }
}
