package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы страницы заказа
    // Кнопка подтверждения cookie
    private final By cookieButton = By.id("rcc-confirm-button");
    // Поле ввода имени
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    // Поле ввода фамилии
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    // Поле ввода адреса
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле выбора станции метро
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    // Поле ввода телефона
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");
    // Опция станции метро в выпадающем списке
    private final By metroStationOption = By.xpath("//div[@class='select-search__select']//button");
    // Поле выбора даты
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // Поле выбора периода аренды
    private final By rentalPeriodField = By.className("Dropdown-control");
    // Календарь для выбора даты
    private final By calendar = By.className("react-datepicker");
    // Сегодняшний день в календаре
    private final By todayDay = By.xpath("//div[contains(@class, 'react-datepicker__day--today') and not(contains(@class, 'react-datepicker__day--disabled'))]");
    // Завтрашний день в календаре
    private final By tomorrowDay = By.xpath("//div[contains(@class, 'react-datepicker__day--today')]/following-sibling::div[not(contains(@class, 'react-datepicker__day--disabled'))][1]");
    // Любой будущий день в календаре
    private final By anyFutureDay = By.xpath("//div[contains(@class, 'react-datepicker__day') and not(contains(@class, 'react-datepicker__day--disabled')) and not(contains(@class, 'react-datepicker__day--outside-month'))]");
    // Чекбокс черного цвета
    private final By colorBlack = By.id("black");
    // Чекбокс серого цвета
    private final By colorGrey = By.id("grey");
    // Поле для комментария курьеру
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // Кнопка "Заказать" на второй странице
    private final By orderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle')]");
    // Кнопка подтверждения заказа "Да"
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");
    // Окно успешного оформления заказа
    private final By orderSuccessModal = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void closeCookieBanner() {
        List<WebElement> cookieButtons = driver.findElements(cookieButton);
        if (!cookieButtons.isEmpty() && cookieButtons.get(0).isDisplayed()) {
            cookieButtons.get(0).click();
        }
    }

    public void fillFirstPage(String name, String surname, String address, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        selectMetroStation();
        driver.findElement(phoneField).sendKeys(phone);
    }

    private void selectMetroStation() {
        driver.findElement(metroField).click();
        WebElement firstStation = wait.until(ExpectedConditions.elementToBeClickable(metroStationOption));
        firstStation.click();
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillSecondPage(String rentalPeriod, String color, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        closeCookieBanner();
        selectDateFromCalendar();
        selectRentalPeriod(rentalPeriod);
        selectColor(color);
        driver.findElement(commentField).sendKeys(comment);
    }

    private void selectDateFromCalendar() {
        driver.findElement(dateField).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(calendar));

        List<WebElement> todayDays = driver.findElements(todayDay);
        if (!todayDays.isEmpty() && todayDays.get(0).isEnabled()) {
            todayDays.get(0).click();
        } else {
            List<WebElement> tomorrowDays = driver.findElements(tomorrowDay);
            if (!tomorrowDays.isEmpty()) {
                tomorrowDays.get(0).click();
            } else {
                List<WebElement> futureDays = driver.findElements(anyFutureDay);
                if (!futureDays.isEmpty()) {
                    futureDays.get(0).click();
                }
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(calendar));
    }

    private void selectRentalPeriod(String period) {
        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        periodField.click();
        By periodOptionLocator = By.xpath(".//div[contains(@class, 'Dropdown-option') and text()='" + period + "']");
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(periodOptionLocator));
        periodOption.click();
    }

    private void selectColor(String color) {
        if ("черный".equals(color) || "чёрный".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if ("серый".equals(color)) {
            driver.findElement(colorGrey).click();
        }
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        confirmButton.click();
    }

    public boolean isOrderSuccess() {
        WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
        return successElement.isDisplayed();
    }
}