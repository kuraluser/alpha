import { TestBed } from '@angular/core/testing';

import { PlanSidePanelTransformationService } from './plan-side-panel-transformation.service';

describe('PlanSidePanelTransformationService', () => {
  let service: PlanSidePanelTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanSidePanelTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
