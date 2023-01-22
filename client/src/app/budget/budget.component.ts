import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {BudgetService} from "./budget.service";
import {OwnedItem} from "./owned-item";
import {MarketService} from "../market/market.service";

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit, OnChanges {

  constructor(private service: BudgetService,
              private marketService: MarketService) {
  }

  budget: number = 0;

  itemsToSell: any = {}

  ownedItems: OwnedItem[] = [];
  @Input()
  state: number = 0;

  @Output()
  stateUpdate = new EventEmitter();


  ngOnInit(): void {
    this.itemsToSell = {}
    const marketId = localStorage.getItem("stock-market-id")
    if (marketId) {
      this.service.getBudget(marketId).subscribe(response => {
        this.budget = response.amount;
        this.ownedItems = response.items;
      })
    }
  }

  ngOnChanges(): void {
    console.log(this.state)
    this.ngOnInit()
  }

  sellItem(itemName: string) {
    const marketId = localStorage.getItem("stock-market-id");
    if (marketId && this.itemsToSell[itemName] > 0) {
      this.marketService.initiateSale(marketId, itemName, this.itemsToSell[itemName]).subscribe(async () => {
        await new Promise( resolve => setTimeout(resolve, 100) )
        this.stateUpdate.emit()
        this.ngOnInit()
      })
    }
  }
}
