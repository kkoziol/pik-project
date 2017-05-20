import {Injectable} from "@angular/core";
import {Jsonp} from "@angular/http";

import 'rxjs/add/operator/map';
import {AuthorizationHttp} from "../authorizationHttp/authorizationHttp";

@Injectable()
export class EBayService {
  constructor(private authorizationHttp: AuthorizationHttp) {
  }

  getMainCategories() {
    return this.authorizationHttp.get("/categories/maincategories");
  }

  getSbsCategoriesByParentId(parentId: number) {
    return this.authorizationHttp.get("/categories/subcategories/" + parentId);
  }

  getSpecificsCategoriesById(categoryId: number) {
    return this.authorizationHttp.get("/categories/categoryspecifics/" + categoryId);
  }

  getBestMatchCategory(keyword: string) {
    return this.authorizationHttp.get("/categories/bestmach/" + keyword);
  }

  getCategoryNameById(categoryId: number) {
    return this.authorizationHttp.get("/categories/categoryname/" + categoryId);
  }

  getItemsByKeyWord(keyword: string) {
    return this.authorizationHttp.get("/items/search/" + keyword);
  }

  getItemsByKeyWordAndCategory(keyword: string, categoryId: number) {
    return this.authorizationHttp.get("/items/search/" + keyword + "/" + categoryId);
  }

  getItemsByKeyWordAndCategoryAndMinMaxPrice(keyword: string, categoryId: number, minPrice: number, maxPrice: number) {
    return this.authorizationHttp.get("/items/search/" + keyword + "/" + categoryId + "/" + minPrice + "/" + maxPrice);
  }

  getBestItemByKeyWord(keyword: string) {
    return this.authorizationHttp.get("/items/bestmach/item/" + keyword);
  }

  getCheapestItemByKeyWordInCategory(keyword: string, categoryId: number) {
    return this.authorizationHttp.get("/items/cheapest/item/" + keyword + "/" + categoryId);
  }
}
