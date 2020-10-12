import { TestBed } from '@angular/core/testing';

import { ExamSharingService } from './exam-sharing.service';

describe('ExamSharingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExamSharingService = TestBed.get(ExamSharingService);
    expect(service).toBeTruthy();
  });
});
