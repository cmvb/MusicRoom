import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TercerosEditComponent } from './tercerosEdit.component';

describe('TercerosEditComponent', () => {
  let component: TercerosEditComponent;
  let fixture: ComponentFixture<TercerosEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TercerosEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TercerosEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
