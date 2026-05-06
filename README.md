# testForApplication

This is a Playwright testing project.

## Prerequisites

- Node.js (v18 or newer recommended)
- npm or yarn or pnpm

## Installation

1. Install dependencies:
```bash
npm install
```

2. Install Playwright browsers:
```bash
npx playwright install
```

## Running Tests

Run all tests:
```bash
npx playwright test
```

Run tests in UI mode:
```bash
npx playwright test --ui
```

Run tests for a specific project (e.g., Chromium):
```bash
npx playwright test --project=chromium
```

## Viewing Test Reports

If any tests fail, or if you have configured Playwright to always generate a report, you can view it by running:
```bash
npx playwright show-report
```

## Project Structure

- `tests/`: Contains the test files.
- `playwright.config.ts`: Playwright configuration file.
- `test-results/`: (Generated) Contains artifacts of test runs (traces, videos, etc.).
- `playwright-report/`: (Generated) Contains HTML reports of test runs.
