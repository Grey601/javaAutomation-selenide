package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {

    public String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldOrderACard() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldOrderACardCityLowercaseLetters() {
        $("[data-test-id='city'] input").setValue("санкт-петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldOrderACardCityUppercaseLetters() {
        $("[data-test-id='city'] input").setValue("САНКТ-ПЕТЕРБУРГ");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldNotOrderACardCityIsNotFromTheList() {
        $("[data-test-id='city'] input").setValue("Таганрог");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotOrderACardCityEng() {
        $("[data-test-id='city'] input").setValue("Moscow");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotOrderACardCityLat() {
        $("[data-test-id='city'] input").setValue("Moskva");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotOrderACardNoCity() {
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldOrderACard3DaysAfterCurrentDate() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldNotOrderACard2DaysAfterCurrentDate() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".calendar-input .input_invalid .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldNotOrderACardNoDate() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        //String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
       // $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".calendar-input .input_invalid .input__sub").shouldHave(Condition.text("Неверно введена дата"));
    }

    @Test
    public void shouldOrderACardManyDaysAfterCurrentDate() {
        //не сильно важно, но при достаточно большом addDays, год в currentDate может стать пятизначным, что нарушит паттерн yyyy
        //тогда используются первые 4 цифры пятизначного значения года, начиная с тысячного года, что приведет к ошибке в поле дата -
        // - Заказ на выбранную дату невозможен. Проверено.
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(2_700_000, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldOrderACardNameLowercaseLetters() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("гревцов сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldOrderACardNameUppercaseLetters() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("ГРЕВЦОВ СЕРГЕЙ");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldOrderACardNameWithHyphen() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов-Платонов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldNotOrderACardNameLat() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Grevtsov Sergei");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotOrderACardNameDigit() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Грев3ов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotOrderACardNameSymbol() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Грев%ов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotOrderACardWithoutName() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotOrderACardSpaceAsAName() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue(" ");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldOrderACardHyphenAsAName() {
        //но так не должно быть
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("-");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void shouldNotOrderACardPhoneWithoutPlus() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("79164786726");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotOrderACardWithoutPhone() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotOrderACardPhone12Digits() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("791647867264");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotOrderACardPhone10Digits() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("7916478672");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotOrderACardLettersAndSymbolsInPhoneField() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+helloworldd");
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotOrderACardWithoutCheckbox() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Гревцов Сергей");
        $("[data-test-id='phone'] input").setValue("+79164786726");
        //$("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}