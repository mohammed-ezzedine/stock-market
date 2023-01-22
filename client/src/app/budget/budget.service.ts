import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {BudgetResponse} from "./budget.response";

@Injectable()
export class BudgetService {

  apiUrl = environment.apiUrl + "/budget";

  constructor(private http: HttpClient) {
  }

  getBudget(marketId: string): Observable<BudgetResponse> {
    return this.http.get<BudgetResponse>(this.apiUrl + "/" + marketId)
  }
}
