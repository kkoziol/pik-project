import {Component, OnInit} from '@angular/core';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import {CategoryType, Item, Properties} from '../eBay/eBay.model';
import {until} from 'selenium-webdriver';
import elementIsNotSelected = until.elementIsNotSelected;

@Component({
  selector: 'my-orders',
  templateUrl: './orders.component.html',
  styleUrls: [ './orders.component.scss' ],
  providers: [EBayService]
})

export class OrdersComponent implements OnInit {
  query: string;
  minCost: number;
  maxCost: number;
  categoryList: CategoryType[];
  selectedCategories: CategoryType[];
  itemList: Item[];
  properties: Properties[];
  selectedProperties: {};

  constructor(private ebayService: EBayService) {
    this.selectedCategories = [];
    this.properties = [];
    this.selectedProperties = {};
  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {

    this.ebayService.getMainCategories()
      .subscribe(data => this.categoryList = data.map(elem => CategoryType.copy(elem)),
        error2 => console.log("Zly request"));
  }

  submit() {
    if (this.selectedCategories.length !== 0) {
      if (this.query !== '') {
        this.ebayService.getItemsByKeyWordAndCategory(this.query, this.selectedCategories[this.selectedCategories.length - 1].categoryID)
          .subscribe(data => {
              this.itemList = data
            },
            error2 => console.log('ERROR'));
      }
      else {
        console.log('WRONG QUERY PARAMETERS');
      }
    }
    else {
      if (this.query !== '') {
        this.ebayService.getItemsByKeyWord(this.query)
          .subscribe(data => this.itemList = data,
            error2 => console.log('ERROR'));
      }
      else {
        console.log('WRONG QUERY PARAMETERS');
      }
    }

  }

  addProperties = (type,value) => {
    this.selectedProperties[type] = value;
    console.log(this.selectedProperties);
  };

  chooseMainCategory = (categoryName: string) => {

    const newSelected = this.categoryList.find(category => category.categoryName === categoryName);

    this.selectedCategories = [];
    this.selectedCategories.push(newSelected);

    this.ebayService.getSbsCategoriesByParentId(newSelected.categoryID)
      .subscribe(data => this.selectedCategories[this.selectedCategories.length - 1].childrenCategories = data.map(elem => CategoryType.copy(elem)),
        error2 => console.log("Zly request"),
        () => {
          console.log(this.selectedCategories)
        });

    this.ebayService.getSpecificsCategoriesById(newSelected.categoryID)
      .subscribe(data => {
          let item: Properties;
          this.properties = [];
          for (let type in data) {
            item = new Properties();
            item.type = type;
            item.value = data[type];
            console.log(item);
            this.properties.push(item);
          }
          console.log(this.properties)
        },
        error2 => console.log("Zly request"),
        () => {
          console.log(this.selectedCategories)
        });
  };

  chooseCategory = (categoryName: string) => {
    //TODO Refactor shity kod ale w przy takim czasie odopowiedzzi z serwera nie ma sensu przyspieszyc
    let newSelected;
    console.log("KLIK",categoryName);
    if (this.selectedCategories.length > 0) {
      newSelected = this.selectedCategories.find(category =>
      category.childrenCategories.find(categoryChild => categoryChild.categoryName === categoryName) !== null)
        .childrenCategories.find(category => category.categoryName === categoryName);
    } else {
      newSelected = this.categoryList.find(category => category.categoryName === categoryName);
    }

    if (newSelected) {
      const tmp = this.selectedCategories.find(cat => {
        return cat.categoryID === newSelected.categoryParentID[0];
      });

      this.selectedCategories = this.selectedCategories.slice(
        0, this.selectedCategories.indexOf(tmp) + 1);

      this.selectedCategories.push(newSelected);

      this.ebayService.getSbsCategoriesByParentId(newSelected.categoryID)
        .subscribe(data => this.selectedCategories[this.selectedCategories.length - 1].childrenCategories =
            data.map(elem => CategoryType.copy(elem)).filter(cat => cat.categoryID !== newSelected.categoryID),
          error2 => console.log("Zly request"),
          () => {
            console.log(this.selectedCategories)
          });


      this.ebayService.getSpecificsCategoriesById(newSelected.categoryID)
        .subscribe(data => {
            let item: Properties;
            this.properties = [];
            for (let type in data) {
              item = new Properties();
              item.type = type;
              item.value = data[type];
              console.log(item);
              this.properties.push(item);
            }
            console.log(this.properties)
          },
          error2 => console.log("Zly request"),
          () => {
            console.log(this.selectedCategories)
          });
    }
  }

}

