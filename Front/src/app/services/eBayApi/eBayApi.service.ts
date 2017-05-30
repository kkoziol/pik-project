import {Injectable} from "@angular/core";
import {Jsonp} from "@angular/http";

import 'rxjs/add/operator/map';
import {AuthorizationHttp} from "../authorizationHttp/authorizationHttp";
import {CategoryType} from "../../components/eBay/eBay.model";

@Injectable()
export class EBayService {
  constructor(private authorizationHttp: AuthorizationHttp) {
  }

  getMainCategories() {
    return this.authorizationHttp.get("/ebay/categories/maincategories").map(res =>  res.json())
  }

  getSbsCategoriesByParentId(parentId: string) {
    return this.authorizationHttp.get("/ebay/categories/subcategories/" + parentId).map(res =>  res.json());
  }

  getSpecificsCategoriesById(categoryId: string) {
    return this.authorizationHttp.get("/ebay/categories/specifics/" + categoryId).map(res =>  res.json());
  }

  getBestMatchCategory(keyword: string) {
    return this.authorizationHttp.get("/ebay/categories/bestmach/" + keyword);
  }

  getCategoryNameById(categoryId: number) {
    return this.authorizationHttp.get("/ebay/categories/categoryname/" + categoryId);
  }

  getItemsByKeyWord(keyword: string, pageNumber: number) {
    return this.authorizationHttp.get("/ebay/items/search/" + keyword + "/" + pageNumber).map(res =>  res.json());
  }

  getItemsByKeyWordAndCategory(keyword: string, categoryId: string, pageNumber: number) {
    return this.authorizationHttp.get("/ebay/items/search/" + keyword + "/" + categoryId + "/" + pageNumber).map(res =>  res.json());
  }

  getItemsByKeyWordAndCategoryAndMinMaxPrice(keyword: string, categoryId: number, minPrice: number, maxPrice: number) {
    return this.authorizationHttp.get("/ebay/items/search/" + keyword + "/" + categoryId + "/" + minPrice + "/" + maxPrice);
  }

  getBestItemByKeyWord(keyword: string) {
    return this.authorizationHttp.get("/ebay/items/bestmach/item/" + keyword);
  }

  getCheapestItemByKeyWordInCategory(keyword: string, categoryId: number) {
    return this.authorizationHttp.get("/ebay/items/cheapest/item/" + keyword + "/" + categoryId);
  }
  
  putOrderPreferences(username: string, data: {}){
  	return  this.authorizationHttp.post("/orders/"+username, data);
  }
}
