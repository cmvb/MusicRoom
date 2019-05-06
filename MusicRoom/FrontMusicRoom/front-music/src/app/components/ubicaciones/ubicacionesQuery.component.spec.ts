import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UbicacionesQueryComponent } from './ubicacionesQuery.component';

describe('UbicacionesQueryComponent', () => {
  let component: UbicacionesQueryComponent;
  let fixture: ComponentFixture<UbicacionesQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UbicacionesQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UbicacionesQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
