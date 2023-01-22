import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'client';

  state: number = 1; // dummy way to make child components reload

  getMarketId() {
    return localStorage.getItem("stock-market-id")
  }

  handleStateUpdate() {
    this.state++;
  }
}
