import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { consultaMttoComponent } from './consultaMtto.component';

describe('consultaMttoComponent', () => {
  let component: consultaMttoComponent;
  let fixture: ComponentFixture<consultaMttoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ consultaMttoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(consultaMttoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});