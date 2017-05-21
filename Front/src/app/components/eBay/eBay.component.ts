import {Component, OnInit, Optional} from '@angular/core';
import {AuthorizationService} from '../../services/authorization/authorization.service';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import {CategoryType} from './eBay.model';
import {until} from 'selenium-webdriver';
import elementIsNotSelected = until.elementIsNotSelected;

@Component({
  selector: 'my-eBay',
  templateUrl: './eBay.component.html',
  styleUrls: ['./eBay.component.scss'],
  providers: [EBayService]
})

export class EBayComponent implements OnInit {
  items: Observable<string[]>;
  query: string;
  minCost: number;
  maxCost: number;
  categoryList: CategoryType[];
  selectedCategory: CategoryType;

  constructor(private ebayService: EBayService) {

  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {

    this.ebayService.getMainCategories()
      .subscribe(data => this.categoryList = data,
        error2 => console.log("Zly request"));
  }

  submit() {
    // if(this.selectedCategory.value !== 0){}
    // else if(this.selectedCategory.value !== 0 && this.minCost && this.maxCost){}
    // else {}
    console.log(this.categoryList);
  }

  chooseCategory = (category: CategoryType) => {
    this.selectedCategory = category;
  }

}
