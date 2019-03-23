import { TestBed, inject } from '@angular/core/testing';

import { EmployeeReimbursementService } from './employee-reimbursement.service';

describe('EmployeeReimbursementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmployeeReimbursementService]
    });
  });

  it('should be created', inject([EmployeeReimbursementService], (service: EmployeeReimbursementService) => {
    expect(service).toBeTruthy();
  }));
});
