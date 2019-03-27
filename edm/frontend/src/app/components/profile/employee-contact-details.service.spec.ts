import { TestBed, inject } from '@angular/core/testing';

import { EmployeeContactDetailsService } from './employee-contact-details.service';

describe('EmployeeContactDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmployeeContactDetailsService]
    });
  });

  it('should be created', inject([EmployeeContactDetailsService], (service: EmployeeContactDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
