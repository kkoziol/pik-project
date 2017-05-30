import {Injectable} from "@angular/core";
import {AuthorizationHttp} from "../authorizationHttp/authorizationHttp";

@Injectable()
export class OrdersService {

  ordersList
  constructor(private authorizationHttp: AuthorizationHttp) {
  }


  getSbsCategoriesByParentId(parentId: number) {
    return this.authorizationHttp.get("/categories/subcategories/" + parentId).map(res =>  res.json());
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
    return this.authorizationHttp.get("/items/search/" + keyword).map(res =>  res.json());
  }

  getItemsByKeyWordAndCategory(keyword: string, categoryId: string) {
    return this.authorizationHttp.get("/items/search/" + keyword + "/" + categoryId).map(res =>  res.json());
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

