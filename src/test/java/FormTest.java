import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;

public class FormTest {

    private LocalDate today = LocalDate.now();
    private LocalDate date = today.plusDays(7);
    private int day = date.getDayOfMonth();
    private String dayToFind = String.valueOf(day);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String todayString = today.format(formatter);
    private String dateString = date.format(formatter);

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
        form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
        form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldSubmitWithChoosing() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys("Мо");
        $$(".menu-item").find(exactText("Москва")).click();
        form.$(cssSelector(".input__box .icon-button")).click();
        $$(".calendar__day").find(exactText(dayToFind)).click();
        form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
        form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
    }
}
