import { TestBed } from '@angular/core/testing';

import { NodejsService } from './nodejs.service';

describe('NodejsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NodejsService = TestBed.get(NodejsService);
    expect(service).toBeTruthy();
  });
});
