package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By orderButtonTop = By.xpath("//button[text()='Заказать']");
    private final By orderButtonBottom = By.xpath("//button[text()='Заказать']/ancestor::div[contains(@class, 'Home_FinishButton')]");
    private final By cookieButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void closeCookieBanner() {
        List<WebElement> cookieButtons = driver.findElements(cookieButton);
        if (!cookieButtons.isEmpty()) {
            cookieButtons.get(0).click();
        }
    }

    public String getQuestionText(int index) {
        String questionId = "accordion__heading-" + index;
        WebElement question = driver.findElement(By.id(questionId));
        return question.getText();
    }

    public void clickQuestion(int index) {
        closeCookieBanner();

        String questionId = "accordion__heading-" + index;
        WebElement question = driver.findElement(By.id(questionId));
        question.sendKeys(Keys.PAGE_DOWN);

        By questionLocator = By.id(questionId);
        question = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        question.click();

        By answerLocator = By.id("accordion__panel-" + index);
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
    }

    public String getAnswerText(int index) {
        By answerLocator = By.id("accordion__panel-" + index);
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText();
    }

    public void clickOrderButtonTop() {
        closeCookieBanner();
        if (driver.getClass().getSimpleName().contains("Firefox")) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
        }
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        button.click();
    }

    public void clickOrderButtonBottom() {
        closeCookieBanner();
        if (driver.getClass().getSimpleName().contains("Firefox")) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.END);
        }
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        button.click();
    }
}