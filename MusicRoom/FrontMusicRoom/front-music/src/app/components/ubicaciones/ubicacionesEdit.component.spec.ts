import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UbicacionesEditComponent } from './ubicacionesEdit.component';

describe('UbicacionesEditComponent', () => {
  let component: UbicacionesEditComponent;
  let fixture: ComponentFixture<UbicacionesEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UbicacionesEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UbicacionesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
