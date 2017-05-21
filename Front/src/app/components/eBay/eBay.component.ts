import {Component, OnInit, Optional} from '@angular/core';
import {AuthorizationService} from '../../services/authorization/authorization.service';
import {EBayService} from '../../services/eBayApi/eBayApi.service';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';
import {Category} from './eBay.model';
import {until} from 'selenium-webdriver';
import elementIsNotSelected = until.elementIsNotSelected;

@Component({
  selector: 'my-eBay',
  templateUrl: './eBay.component.html',
  styleUrls: ['./eBay.component.scss'],
  providers: [EBayService]
})

export class EBayComponent implements OnInit {
  items: Observable<string[]>;
  query: string;
  minCost: number;
  maxCost: number;
  categoryList: Category[];
  selectedCategory: Category;

  constructor(private ebayService: EBayService) {
    this.categoryList = [
      new Category('Wybierz kategorie', 353),
      new Category('Antyki i Sztuka', 353),
      new Category('Biuro i Szkoła', 0),
      new Category('Biżuteria i Zegarki', 0),
      new Category('Delikatesy', 0),
      new Category('Dla Dzieci', 0),
      new Category('Dom i Meble', 0),
      new Category('Filmy i DVD', 0),
      new Category('Firma i Przemysł', 0),
      new Category('Fotografia i Kamery', 0),
      new Category('Gry i Konsole', 0),
      new Category('Instrumenty Muzyczne', 0),
      new Category('Kolekcje', 0),
      new Category('Komputery i Tablety', 0),
      new Category('Książki', 0),
      new Category('Majsterkowanie', 0),
      new Category('Modelarstwo', 0),
      new Category('Monety i Banknoty', 0),
      new Category('Motoryzacja: Części', 0),
      new Category('Motoryzacja: Pojazdy', 0),
      new Category('Muzyka', 0),
      new Category('Nieruchomości', 0),
      new Category('Odzież, Buty i Dodatki', 0),
      new Category('Ogród i Taras', 0),
      new Category('Podróże', 0),
      new Category('Sport i Turystyka', 0),
      new Category('Sprzęt AGD', 0),
      new Category('Telefony i Akcesoria', 0),
      new Category('TV, Audio i Video', 0),
      new Category('Zabawki', 0),
      new Category('Zdrowie i Uroda', 0),
      new Category('Znaczki', 0),
      new Category('Zwierzęta', 0),
      new Category('Wszystko inne', 0)];
    this.selectedCategory = this.categoryList[0];
  }

  private searchTermStream = new Subject<string>();

  search(term: string) {
    this.searchTermStream.next(term);
  }

  ngOnInit() {
  }

  submit() {
    if(this.selectedCategory.value !== 0){}
    else if(this.selectedCategory.value !== 0 && this.minCost && this.maxCost){}
    else {}
    console.log(this.ebayService.getMainCategories().subscribe());
  }

  chooseCategory = (category: Category) => {
    this.selectedCategory = category;
  }

}
