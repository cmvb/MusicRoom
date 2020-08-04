import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BandasIntegrantesEditComponent } from './bandasIntegrantesEdit.component';

describe('BandasIntegrantesEditComponent', () => {
  let component: BandasIntegrantesEditComponent;
  let fixture: ComponentFixture<BandasIntegrantesEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BandasIntegrantesEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BandasIntegrantesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
