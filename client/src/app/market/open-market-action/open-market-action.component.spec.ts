import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenMarketActionComponent } from './open-market-action.component';

describe('OpenMarketActionComponent', () => {
  let component: OpenMarketActionComponent;
  let fixture: ComponentFixture<OpenMarketActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OpenMarketActionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpenMarketActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
