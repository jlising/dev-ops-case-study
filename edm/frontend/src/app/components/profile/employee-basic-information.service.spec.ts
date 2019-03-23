import { TestBed, inject } from '@angular/core/testing';

import { EmployeeBasicInformationService } from './employee-basic-information.service';

describe('EmployeeBasicInformationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmployeeBasicInformationService]
    });
  });

  it('should be created', inject([EmployeeBasicInformationService], (service: EmployeeBasicInformationService) => {
    expect(service).toBeTruthy();
  }));
});
