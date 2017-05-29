import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../services/authorization/authorization.service";

@Component({
  selector: 'my-settings',
  templateUrl: './settings.component.html',
  styleUrls: [ './settings.component.scss' ]
})


export class SettingsComponent implements OnInit {

  constructor(private autorizationService: AuthorizationService) { }

  ngOnInit(): void {
  }
}
