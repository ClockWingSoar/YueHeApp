## Test Strategy and Plan

### Scope
- API: Spring Boot services (unit + integration)
- Web: Next.js + Redux (unit + component + e2e)

### Levels
- Unit: JUnit5, React Testing Library
- Integration: SpringBootTest with Testcontainers (future), API contract tests
- E2E: Playwright (headless CI) against dev server

### Quality Gates
- Coverage thresholds: backend 80% lines, frontend 80% statements
- CI must be green on lint, type-check, tests

### Test Data
- Seed data via SQL scripts or builders

### Reporting
- JUnit XML for CI, coverage reports, flaky test tracking

