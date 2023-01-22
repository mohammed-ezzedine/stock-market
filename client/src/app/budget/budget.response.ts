import {OwnedItem} from "./owned-item";

export interface BudgetResponse {
  amount: number;
  items: OwnedItem[]
}
