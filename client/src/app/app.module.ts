import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {NzButtonModule} from "ng-zorro-antd/button";
import { OpenMarketActionComponent } from './market/open-market-action/open-market-action.component';
import {MarketService} from "./market/market.service";
import {HttpClientModule} from "@angular/common/http";
import { MarketStockComponent } from './market/market-stock/market-stock.component';
import {NzTableModule} from "ng-zorro-antd/table";
import {NzGridModule} from "ng-zorro-antd/grid";
import { BudgetComponent } from './budget/budget.component';
import {BudgetService} from "./budget/budget.service";
import {NzTypographyModule} from "ng-zorro-antd/typography";
import {NzStatisticModule} from "ng-zorro-antd/statistic";
import {NzSelectModule} from "ng-zorro-antd/select";
import {FormsModule} from "@angular/forms";
import {NzInputNumberModule} from "ng-zorro-antd/input-number";

@NgModule({
  declarations: [
    AppComponent,
    OpenMarketActionComponent,
    MarketStockComponent,
    BudgetComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NzButtonModule,
    NzTableModule,
    NzGridModule,
    NzTypographyModule,
    NzStatisticModule,
    NzSelectModule,
    FormsModule,
    NzInputNumberModule
  ],
  providers: [MarketService, BudgetService],
  bootstrap: [AppComponent]
})
export class AppModule { }
