import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioEditComponent } from './UsuarioEdit.component';

describe('UsuarioEdit', () => {
  let component: UsuarioEditComponent;
  let fixture: ComponentFixture<UsuarioEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UsuarioEditComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
