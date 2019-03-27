import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeSearchDialogComponent } from './employee-search-dialog.component';

describe('EmployeeSearchDialogComponent', () => {
  let component: EmployeeSearchDialogComponent;
  let fixture: ComponentFixture<EmployeeSearchDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeSearchDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeSearchDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
