import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IteradorMttoComponent } from './iteradormtto.component';

describe('iteradorMttoComponent', () => {
  let component: IteradorMttoComponent;
  let fixture: ComponentFixture<IteradorMttoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IteradorMttoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IteradorMttoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
