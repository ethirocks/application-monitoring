import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NetworkStatComponent } from './network-stat.component';

describe('NetworkComponent', () => {
  let component: NetworkStatComponent;
  let fixture: ComponentFixture<NetworkStatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NetworkStatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NetworkStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
