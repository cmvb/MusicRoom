import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TercerosQueryComponent } from './tercerosQuery.component';

describe('TercerosQueryComponent', () => {
  let component: TercerosQueryComponent;
  let fixture: ComponentFixture<TercerosQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TercerosQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TercerosQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
