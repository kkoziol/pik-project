import {Properties} from '../eBay/eBay.model';

export class UserPreference {
  categoryId: string;
  priceMin: number;
  priceMax: number;
  conditions: string[];
  deliveryOptions: string;
  categorySpecifics: {};
  keyword: string;
  dateAndTime: Date;
}

export class Order {
   orderId: number;
   userPreference: UserPreference; 
}


export class FoundResult {
  url: string;
}



