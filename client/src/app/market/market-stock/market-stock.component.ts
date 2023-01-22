import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {MarketService} from "../market.service";
import {ItemStock} from "../item-stock";

@Component({
  selector: 'app-market-stock',
  templateUrl: './market-stock.component.html',
  styleUrls: ['./market-stock.component.scss']
})
export class MarketStockComponent implements OnInit, OnChanges {

  constructor(private service: MarketService) {
  }

  items: ItemStock[] = [];

  itemsToBuy: any = {};

  @Output()
  stateUpdate = new EventEmitter();

  @Input()
  state: number = 0;

  ngOnInit(): void {
    this.itemsToBuy = {}
    const marketId = localStorage.getItem("stock-market-id");
    if (marketId) {
      this.service.getMarket(marketId).subscribe(response => this.items = response.items)
    }
  }

  ngOnChanges(): void {
    console.log(this.state)
    this.ngOnInit()
  }

  buyItem(itemName: string) {
    const marketId = localStorage.getItem("stock-market-id");
    if (marketId && this.itemsToBuy[itemName] > 0) {
      this.service.initiateTransaction(marketId, itemName, this.itemsToBuy[itemName]).subscribe(async () => {
        await new Promise( resolve => setTimeout(resolve, 100) )
        this.stateUpdate.emit()
        this.ngOnInit()
      })
    }
  }
}
