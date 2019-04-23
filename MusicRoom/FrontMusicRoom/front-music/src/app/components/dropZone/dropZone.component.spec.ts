import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {DropZoneComponent} from './dropZone.component';

describe('dropZoneComponent', () => {
  let component: DropZoneComponent;
  let fixture: ComponentFixture<DropZoneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DropZoneComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DropZoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
