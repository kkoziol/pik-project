export class CategoryType {
  bestOfferEnabled: boolean;
  autoPayEnabled: boolean;
  b2bVatEnabed: boolean;
  catalogEnabled: boolean;
  categoryId: number;
  categoryLevel: number;
  categoryName: string;
  categoyParentId: string[];
  categoyParentName: string[];
  productSearchPageAvailable: boolean;


}

export class Item {
  itemId: number;
  title: string;
  subtitle: string;
  primaryCategory: string[];
  galleryURL: string;
  viewItemURL: string;
  productId: string[];
}
