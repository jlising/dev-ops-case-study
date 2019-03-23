import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeProfileDialogComponent } from './employee-profile-dialog.component';

describe('EmployeeProfileDialogComponent', () => {
  let component: EmployeeProfileDialogComponent;
  let fixture: ComponentFixture<EmployeeProfileDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeProfileDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeProfileDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
