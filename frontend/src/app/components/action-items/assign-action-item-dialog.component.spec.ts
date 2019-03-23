import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignActionItemDialogComponent } from './assign-action-item-dialog.component';

describe('AssignActionItemDialogComponent', () => {
  let component: AssignActionItemDialogComponent;
  let fixture: ComponentFixture<AssignActionItemDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignActionItemDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignActionItemDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
