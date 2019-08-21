import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SalasQueryComponent } from './salasQuery.component';

describe('SalasQueryComponent', () => {
  let component: SalasQueryComponent;
  let fixture: ComponentFixture<SalasQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalasQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalasQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
