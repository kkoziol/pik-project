import {Properties} from '../eBay/eBay.model';

export class UserPreference {
  categoryID: string;
  priceMin: number;
  priceMax: number;
  conditions: string[];
  deliveryOptions: string;
  categorySpecifics: {};
  keyword: string;
  dateAndTime: string;
}


export class FoundResult {
  url: string;
}

export class Orders {
  date: Date;
  minPrice: number;
  maxPrice: number;
  condition: string;
  deliveryOptions: string;
  properties: {};
  keyword: string;

}

