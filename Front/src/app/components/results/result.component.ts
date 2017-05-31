import {Component, OnInit} from '@angular/core';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {Subject} from 'rxjs/Subject';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import {CategoryType, Item, Properties} from './eBay.model';
import {until} from 'selenium-webdriver';
import elementIsNotSelected = until.elementIsNotSelected;
import {FoundResultService} from "../../services/foundResult/foundResult.service";
import {FoundResult, UserPreference} from "../orders/orders.model";
import {AuthorizationHttp} from "../../services/authorizationHttp/authorizationHttp";
import {AuthorizationService} from "../../services/authorization/authorization.service";

@Component({
  selector: 'my-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss'],
  providers: [FoundResultService]
})

export class ResultComponent implements OnInit {
  query: string;
  categoryList: CategoryType[];
  selectedCategories: CategoryType[];
  itemList: FoundResult[];
  properties: Properties[];
  selectedProperties: {};
  pageCounter: number;
  someData: boolean;
  nothingElse: boolean;

  constructor(private foundResult: FoundResultService,private authorcationHttp:  AuthorizationHttp,private authotrizationService: AuthorizationService) {
    this.selectedCategories = [];
    this.properties = [];
    this.selectedProperties = {};
    this.pageCounter = 1;
    this.someData = false;
    this.nothingElse = false;
  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {
    this.authorcationHttp.get("/foundresults/list/" + this.authotrizationService.username).map(res => res.json()).subscribe(data => {
      this.itemList = data;console.log(data)},error2 => {})
  }

}
