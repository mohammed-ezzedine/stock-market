import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {OpenMarketResponse} from "./open-market.response";
import {MarketStockResponse} from "./market-stock.response";

@Injectable()
export class MarketService {
  apiUrl = environment.apiUrl + "/market";

  constructor(private http: HttpClient) {
  }

  openMarket(): Observable<OpenMarketResponse> {
    return this.http.post<OpenMarketResponse>(this.apiUrl + "/init", {});
  }

  getMarket(id: string): Observable<MarketStockResponse> {
    return this.http.get<MarketStockResponse>(this.apiUrl + "/" + id);
  }

  initiateTransaction(marketId: string, itemName: string, quantity: number): Observable<void> {
    let body = {
      itemName: itemName,
      quantity: quantity
    };
    return this.http.post<void>(this.apiUrl + "/" + marketId + "/buy", body);
  }

  initiateSale(marketId: string, itemName: string, quantity: number): Observable<void> {
    let body = {
      itemName: itemName,
      quantity: quantity
    };
    return this.http.post<void>(this.apiUrl + "/" + marketId + "/sell", body);
  }
}
