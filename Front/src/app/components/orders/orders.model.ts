import {Properties} from '../eBay/eBay.model';

export class UserPreference {
  categoryID: string;
  minPrice: number;
  maxPrice: number;
  condition: string;
  deliveryOptions: string;
  properties: {};
  keyword: string;

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
