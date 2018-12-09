import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarNetworkComponent } from './war-network.component';

describe('WarNetworkComponent', () => {
  let component: WarNetworkComponent;
  let fixture: ComponentFixture<WarNetworkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarNetworkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarNetworkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
