import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VCodeComponent } from './v-code.component';

describe('VCodeComponent', () => {
  let component: VCodeComponent;
  let fixture: ComponentFixture<VCodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VCodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
