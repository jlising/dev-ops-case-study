import { TestBed, inject } from '@angular/core/testing';

import { EmployeeService } from './employees.service';

describe('EmployeesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmployeesService]
    });
  });

  it('should be created', inject([EmployeesService], (service: EmployeesService) => {
    expect(service).toBeTruthy();
  }));
});
