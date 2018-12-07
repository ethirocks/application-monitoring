import { StaticGraphModule } from './static-graph.module';

describe('StaticGraphModule', () => {
  let staticGraphModule: StaticGraphModule;

  beforeEach(() => {
    staticGraphModule = new StaticGraphModule();
  });

  it('should create an instance', () => {
    expect(staticGraphModule).toBeTruthy();
  });
});
