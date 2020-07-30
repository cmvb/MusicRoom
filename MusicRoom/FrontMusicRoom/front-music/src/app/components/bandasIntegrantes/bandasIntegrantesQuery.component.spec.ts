import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BandasIntegrantesQueryComponent } from './bandasIntegrantesQuery.component';

describe('BandasIntegrantesQueryComponent', () => {
  let component: BandasIntegrantesQueryComponent;
  let fixture: ComponentFixture<BandasIntegrantesQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BandasIntegrantesQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BandasIntegrantesQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
