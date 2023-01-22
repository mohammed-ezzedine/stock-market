import { Component, OnInit } from '@angular/core';
import {MarketService} from "../market.service";

@Component({
  selector: 'app-open-market-action',
  templateUrl: './open-market-action.component.html',
  styleUrls: ['./open-market-action.component.scss']
})
export class OpenMarketActionComponent implements OnInit {

  constructor(private service: MarketService) { }

  ngOnInit(): void {
  }

  openMarket() {
    this.service.openMarket().subscribe(response => localStorage.setItem("stock-market-id", response.marketId))
  }

}
