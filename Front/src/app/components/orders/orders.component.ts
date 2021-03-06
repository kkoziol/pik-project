import {Component, OnInit} from '@angular/core';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {AuthorizationService} from "../../services/authorization/authorization.service";
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {UserPreference, Order} from './orders.model';

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
  conditions: string[];
  selectedCondition: string;
  userOrders: Order[];
  //pageCounter: number;
  someData: boolean;

  constructor(private ebayService: EBayService, private authotrizationService: AuthorizationService) {
    this.selectedCategories = [];
    this.properties = [];
    this.selectedProperties = {};
    this.conditions = [];
    this.userOrders = [];
    this.someData = false;
  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {
    this.getUserOrders();
    this.ebayService.getMainCategories()
      .subscribe(data => this.categoryList = data.map(elem => CategoryType.copy(elem)),
        error2 => console.log("Zly request"));
    this.conditions = ['New', 'New other (see details)', 'New with defects', 'Manufacturer refurbished', 'Seller refurbished', 'Used', 'Very Good', 'Good', 'Acceptable', 'For parts or not working'];

  }



  addProperties = (type,value) => {
    this.selectedProperties[type] = [value];
    console.log(this.selectedProperties);
  };

  chooseMainCategory = (categoryName: string) => {

    const newSelected = this.categoryList.find(category => category.categoryName === categoryName);

    this.selectedCategories = [];
    this.selectedCategories.push(newSelected);

    console.log(newSelected,this.selectedCategories);

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

  findOffers(){
      let preference: UserPreference;
      preference = new UserPreference(null,null,null,null,null,null,null,null);
      preference.categoryId = this.selectedCategories[this.selectedCategories.length - 1].categoryID;
      preference.priceMin = this.minCost;
      preference.priceMax = this.maxCost;
      preference.conditions = [this.selectedCondition];
      preference.categorySpecifics = this.selectedProperties;
      preference.deliveryOptions = 'Free International shipping';
      preference.keyword = this.query;
      console.log(preference);
      this.ebayService.putOrderPreferences(this.authotrizationService.username, preference)
       .map(res => res.json())
      .subscribe(response => {
          console.log(response.body);
          this.getUserOrders();
        },
        error2 => {
          console.log("Wrong post order");
          return false;
        });
  }

  getUserOrders(){

      this.ebayService.getUserOrders(this.authotrizationService.username)
     .map(res => res.json())
     .subscribe(data => {
              if(!data){
                  console.log('nothing else');
              }else{
                this.someData = true;
                this.userOrders = [];
                this.userOrders = data;
                console.log(this.userOrders)
              }
            },
            error2 => console.log('ERROR'));
  }

  matchCategory(categoryToMatch: string): string{
    if(this.categoryList !== undefined)
      for(let category of this.categoryList){
          if(category.categoryID === categoryToMatch)
            return category.categoryName;
      }
      return 'Category not found';
  }

    deleteOrder(orderId){
    console.log('Deleting order!' + orderId);
    this.ebayService.deleteUserOrder(orderId)
     .map(res => res.json())
     .subscribe(data => {
                console.log(data.deletedOrderId);
                this.getUserOrders();
            },
            error2 => console.log('ERROR while deleting'));
    }
}

