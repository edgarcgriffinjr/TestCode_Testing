package com.example.demo;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FormTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setBaseURL("http://localhost:8080"));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldDisplayTheFormCorrectly() {
        page.navigate("/");

        // Check if the page title is correct
        assertThat(page).hasTitle("Input Form");

        // Check if the heading is present
        assertThat(page.locator("h2")).hasText("Enter Your Details");

        // Check if the form fields exist
        assertThat(page.locator("label[for=\"name\"]")).hasText("Name:");
        assertThat(page.locator("input#name")).isVisible();
        assertThat(page.locator("label[for=\"email\"]")).hasText("Email:");
        assertThat(page.locator("input#email")).isVisible();

        // Check if the submit button exists
        assertThat(page.locator("button[type=\"submit\"]")).hasText("Submit");
    }

    @Test
    void shouldSuccessfullySubmitTheFormAndShowResult() {
        page.navigate("/");

        // Fill the form
        page.fill("input#name", "John Doe");
        page.fill("input#email", "john.doe@example.com");

        // Submit the form
        page.click("button[type=\"submit\"]");

        // Wait for the result page to load
        assertThat(page).hasTitle("Result");

        // Check for success message
        assertThat(page.locator("h2.success-msg")).hasText("Success!");

        // Verify the submitted data is displayed
        assertThat(page.locator("p").filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText("Thank you"))).containsText("John Doe");
        assertThat(page.locator("p").filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText("We received your email address"))).containsText("john.doe@example.com");

        // Check the "Go Back" link
        com.microsoft.playwright.Locator goBackLink = page.locator("a", new Page.LocatorOptions().setHasText("Go Back"));
        assertThat(goBackLink).isVisible();
        assertThat(goBackLink).hasAttribute("href", "/");

        // Click "Go Back" and ensure we are back at the form
        goBackLink.click();
        assertThat(page).hasTitle("Input Form");
    }
}
