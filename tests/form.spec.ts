import { test, expect } from '@playwright/test';

test.describe('Application Form Tests', () => {
  test('should display the form correctly', async ({ page }) => {
    // Navigate to the root URL
    await page.goto('/');

    // Check if the page title is correct
    await expect(page).toHaveTitle('Input Form');

    // Check if the heading is present
    await expect(page.locator('h2')).toHaveText('Enter Your Details');

    // Check if the form fields exist
    await expect(page.locator('label[for="name"]')).toHaveText('Name:');
    await expect(page.locator('input#name')).toBeVisible();
    await expect(page.locator('label[for="email"]')).toHaveText('Email:');
    await expect(page.locator('input#email')).toBeVisible();

    // Check if the submit button exists
    await expect(page.locator('button[type="submit"]')).toHaveText('Submit');
  });

  test('should successfully submit the form and show result', async ({ page }) => {
    // Navigate to the root URL
    await page.goto('/');

    // Fill the form
    await page.fill('input#name', 'John Doe');
    await page.fill('input#email', 'john.doe@example.com');

    // Submit the form
    await page.click('button[type="submit"]');

    // Wait for the result page to load
    await expect(page).toHaveTitle('Result');

    // Check for success message
    await expect(page.locator('h2.success-msg')).toHaveText('Success!');

    // Verify the submitted data is displayed
    await expect(page.locator('p').filter({ hasText: 'Thank you' })).toContainText('John Doe');
    await expect(page.locator('p').filter({ hasText: 'We received your email address' })).toContainText('john.doe@example.com');

    // Check the "Go Back" link
    const goBackLink = page.locator('a', { hasText: 'Go Back' });
    await expect(goBackLink).toBeVisible();
    await expect(goBackLink).toHaveAttribute('href', '/');
    
    // Click "Go Back" and ensure we are back at the form
    await goBackLink.click();
    await expect(page).toHaveTitle('Input Form');
  });
});
