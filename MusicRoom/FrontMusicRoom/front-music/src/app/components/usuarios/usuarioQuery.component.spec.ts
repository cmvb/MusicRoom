import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioQueryComponent } from './UsuarioQuery.component';

describe('UsuarioQuery', () => {
  let component: UsuarioQueryComponent;
  let fixture: ComponentFixture<UsuarioQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UsuarioQueryComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
