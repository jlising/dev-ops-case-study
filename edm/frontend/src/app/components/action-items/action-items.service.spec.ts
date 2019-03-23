import { TestBed, inject } from '@angular/core/testing';

import { ActionItemsService } from './action-items.service';

describe('ActionItemsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ActionItemsService]
    });
  });

  it('should be created', inject([ActionItemsService], (service: ActionItemsService) => {
    expect(service).toBeTruthy();
  }));
});
