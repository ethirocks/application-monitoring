import { WarGraphModule } from './war-graph.module';

describe('WarGraphModule', () => {
  let warGraphModule: WarGraphModule;

  beforeEach(() => {
    warGraphModule = new WarGraphModule();
  });

  it('should create an instance', () => {
    expect(warGraphModule).toBeTruthy();
  });
});
