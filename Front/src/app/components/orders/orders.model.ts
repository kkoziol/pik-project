
export class UserPreference {
  categoryId: string;
  priceMin: number;
  priceMax: number;
  conditions: string[];
  deliveryOptions: string;
  categorySpecifics: {};
  keyword: string;
  dateAndTime: Date;


  constructor(categoryId: string, priceMin: number, priceMax: number, conditions: string[], deliveryOptions: string, categorySpecifics: {}, keyword: string, dateAndTime: Date) {
    this.categoryId = categoryId;
    this.priceMin = priceMin;
    this.priceMax = priceMax;
    this.conditions = conditions;
    this.deliveryOptions = deliveryOptions;
    this.categorySpecifics = categorySpecifics;
    this.keyword = keyword;
    this.dateAndTime = dateAndTime;
  }
}

export class Order {
   userPreference: UserPreference;

  constructor(userPreference: UserPreference) {
    this.userPreference = userPreference;
  }

  static copy(other){
    return new Order(JSON.parse(other.preferencesAsJson));
  }
}


export class FoundResult {
  url: string;
  foundResultId: number;
  order:Order;

  constructor(url: string, foundResultId: number, order: Order) {
    this.url = url;
    this.foundResultId = foundResultId;
    this.order = order;
  }

  static copy(other: FoundResult){
    return new FoundResult(other.url,other.foundResultId,Order.copy(other.order));
  }
}



