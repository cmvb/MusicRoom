import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfViewerthis } from './pdf-viewerthis.util.component';

describe('PdfViewerthis', () => {
  let component: PdfViewerthis;
  let fixture: ComponentFixture<PdfViewerthis>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdfViewerthis ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfViewerthis);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
