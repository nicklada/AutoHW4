package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Nested;
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

    @Nested
    public class FullyValid {

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

    @Nested
    public class FieldCityTests {

        @Test
        void shouldSubmitRequestIfCityWithSmallLetter() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("москва");
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
        void shouldSubmitRequestIfCityWithDash() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Нарьян-Мар");
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
        void shouldNotSubmitRequestIfCityIrrelevant() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Урюпинск");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityInIrrelevantSymbols() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Moskva");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityIsEmpty() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class FieldDateTests {

        @Test
        void shouldNotSubmitRequestIfDateEmpty() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Неверно введена дата")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfDateInvalid() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(todayString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfDateInvalidSymbols() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys("dkjhsfkajhdf");
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Неверно введена дата")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class FieldNameTests {

        @Test
        void shouldSubmitRequestIfInSmallLetters() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("лада николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldSubmitRequestIfWithDash() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева-Петрова");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfEmpty() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfOnlyName() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfOneLetter() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Л Н");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfIrrelevantAmountOfLetters() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лааавалорлываорпдлаворпдвлфорпдлвапрлфвоарпдфвлаопрвадлопрлдфваорфплыоварловоарфловралофврплоаврплорва Ни");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfLatinLetters() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Lada Nikolaeva");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfIrrelevantSymbols() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("123 456");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class FieldTelTests {

        @Test
        void shouldNotSubmitRequestIfEmpty() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithoutPlus() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("79111235678");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithTenNumbers() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+7911123567");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithTwelveNumbers() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+791112356789");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }


    }

    @Nested
    public class FieldAgreementTests {

        @Test
        void shouldNotSubmitRequestWithoutAgreement() {
            open("http://localhost:9999");
            SelenideElement form = $("[action]");
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Москва");
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(dateString);
            form.$(cssSelector("[name=name]")).sendKeys("Лада Николаева");
            form.$(cssSelector("[name=phone]")).sendKeys("+79111235678");
            form.$(byText("Забронировать")).click();
            $(".input_invalid [role=presentation]").shouldHave(Condition.text("Я соглашаюсь"));
        }
    }
}
