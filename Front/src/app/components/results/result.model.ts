import {FoundResult} from "../orders/orders.model";
export class CategoryType {
  autoPayEnabled: boolean;
  b2BVATEnabled: boolean;
  bestOfferEnabled: boolean;
  catalogEnabled: boolean;
  categoryID: string;
  categoryLevel: number;
  categoryName: string;
  categoryParentID: string[];
  categoryParentName: string[];
  productSearchPageAvailable: boolean;
  childrenCategories: CategoryType[];

  constructor(bestOfferEnabled: boolean, autoPayEnabled: boolean, b2bVatEnabled: boolean,
              catalogEnabled: boolean, categoryId: string, categoryLevel: number, categoryName: string,
              categoryParentId: string[], categoryParentName: string[], productSearchPageAvailable: boolean) {
    this.bestOfferEnabled = bestOfferEnabled;
    this.autoPayEnabled = autoPayEnabled;
    this.b2BVATEnabled = b2bVatEnabled;
    this.catalogEnabled = catalogEnabled;
    this.categoryID = categoryId;
    this.categoryLevel = categoryLevel;
    this.categoryName = categoryName;
    this.categoryParentID = categoryParentId;
    this.categoryParentName = categoryParentName;
    this.productSearchPageAvailable = productSearchPageAvailable;
    this.childrenCategories = [];
  }

  static copy(categoryType: CategoryType) {
    return new CategoryType(categoryType.bestOfferEnabled, categoryType.autoPayEnabled, categoryType.b2BVATEnabled,
      categoryType.catalogEnabled, categoryType.categoryID, categoryType.categoryLevel, categoryType.categoryName,
      categoryType.categoryParentID, categoryType.categoryParentName, categoryType.productSearchPageAvailable);
  }
}

export class ItemByCategory {
  name: string;
  value: FoundResult[];


  constructor(name: string, value: FoundResult[]) {
    this.name = name;
    this.value = value;
  }
}



