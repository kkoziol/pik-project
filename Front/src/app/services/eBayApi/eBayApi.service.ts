import {Injectable} from "@angular/core";
import {Jsonp} from "@angular/http";

import 'rxjs/add/operator/map';

@Injectable()
export class EBayService {
  constructor(private jsonp: Jsonp) {}

  search (term: string) {

    let wikiUrl = 'https://192.168.1.72:8800/categories/maincategories';

    let params = new URLSearchParams();
    // params.set('search', term); // the user's search value
    // params.set('action', 'opensearch');
    // params.set('format', 'json');
    // params.set('callback', 'JSONP_CALLBACK');

    // TODO: Add error handling
    return this.jsonp
      .get(wikiUrl, { search: params })
      .map(response => <string[]> response.json()[1]);
  }
}
