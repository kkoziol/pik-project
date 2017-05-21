import {Component, OnInit, Optional} from '@angular/core';
import {AuthorizationService} from '../../services/authorization/authorization.service';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import {CategoryType, Item} from './eBay.model';
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
  itemList: Item[];

  constructor(private ebayService: EBayService) {

  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {

    this.ebayService.getMainCategories()
      .subscribe(data => this.categoryList = data,
        error2 => console.log('ERROR'));
  }

  submit() {
//    if (this.selectedCategory.categoryId !== 0 && this.query !== '') {
//      this.ebayService.getItemsByKeyWordAndCategory(this.query, this.selectedCategory.categoryId)
//      .map(res =>  res.json())
//      .subscribe(data => this.itemList = data,
//        error2 => console.log('ERROR'));
//    }else if (this.selectedCategory.categoryId !== 0 && this.query !== '' && (this.minCost <= this.maxCost) && this.minCost != null && this.maxCost != null) {
//      this.ebayService.getItemsByKeyWordAndCategoryAndMinMaxPrice(this.query, this.selectedCategory.categoryId, this.minCost, this.maxCost)
//      .map(res =>  res.json())
//      .subscribe(data => this.itemList = data,
//        error2 => console.log('ERROR'));
//    }else 
    if (this.query !== '') {
      this.ebayService.getItemsByKeyWord(this.query)
      .map(res =>  res.json())
      .subscribe(data => this.itemList = data,
        error2 => console.log('ERROR'));
    }
    else {
      console.log('WRONG QUERY PARAMETERS');
    }
  }

  chooseCategory = (category: CategoryType) => {
    this.selectedCategory = category;
  }

}
