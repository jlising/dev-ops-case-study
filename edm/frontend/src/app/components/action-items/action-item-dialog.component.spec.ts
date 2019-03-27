import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewActionItemDialogComponent } from './new-action-item-dialog.component';

describe('NewActionItemDialogComponent', () => {
  let component: NewActionItemDialogComponent;
  let fixture: ComponentFixture<NewActionItemDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewActionItemDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewActionItemDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
