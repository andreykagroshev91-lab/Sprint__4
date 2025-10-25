package tests;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    @Parameterized.Parameter
    public String browserName;

    @Parameterized.Parameter(1)
    public String buttonType;

    @Parameterized.Parameter(2)
    public String name;

    @Parameterized.Parameter(3)
    public String surname;

    @Parameterized.Parameter(4)
    public String address;

    @Parameterized.Parameter(5)
    public String phone;

    @Parameterized.Parameter(6)
    public String rentalPeriod;

    @Parameterized.Parameter(7)
    public String color;

    @Parameterized.Parameter(8)
    public String comment;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // Сценарий 1: Верхняя кнопка + первый набор данных
                {"chrome", "top", "Папппа", "Аппапа", "Проспект мира", "89999999999", "сутки", "черный", "Позвонить за час"},
                {"firefox", "top", "Дубина", "Кабана", "Улица дырявая", "89999999999", "сутки", "черный", "Не звонить в домофон"},

                // Сценарий 2: Нижняя кнопка + второй набор данных
                {"chrome", "bottom", "Букля", "Петрося", "Пятка 1905", "+78888888888", "двое суток", "серый", "Оставить у двери"},
                {"firefox", "bottom", "Кабанчик", "Бежит", "Пятка 1905", "+78888888888", "трое суток", "черный", "Доставить до 18:00"}
        });
    }

    @Test
    public void orderScooterTest() {
        setupBrowser();
        OrderPage orderPage = createOrder();
        completeOrder(orderPage);
        verifyOrderSuccess(orderPage);
    }

    private void setupBrowser() {
        if ("chrome".equals(browserName)) {
            setupChrome();
        } else {
            setupFirefox();
        }
    }

    private OrderPage createOrder() {
        MainPage mainPage = new MainPage(driver);

        if (buttonType.equals("top")) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstPage(name, surname, address, phone);
        orderPage.clickNextButton();

        return orderPage;
    }

    private void completeOrder(OrderPage orderPage) {
        orderPage.fillSecondPage(rentalPeriod, color, comment);
        orderPage.clickOrderButton();
        orderPage.confirmOrder();
    }

    private void verifyOrderSuccess(OrderPage orderPage) {
        Assert.assertTrue("Заказ должен быть успешным", orderPage.isOrderSuccess());
    }
}