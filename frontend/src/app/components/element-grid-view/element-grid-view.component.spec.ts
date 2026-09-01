import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElementGridViewComponent } from './element-grid-view.component';

describe('ElementGridViewComponent', () => {
  let component: ElementGridViewComponent;
  let fixture: ComponentFixture<ElementGridViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElementGridViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElementGridViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
