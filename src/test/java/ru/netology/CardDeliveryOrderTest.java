package ru.netology;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

public class CardDeliveryOrderTest {

    @Test
    void shouldSentCardOrderWithAllDataAndComplexElements() {
        Selenide.open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("са");
        $(".input__popup").shouldBe(Condition.visible);
        $(byText("Санкт-Петербург")).click();
        $("[data-test-id=city] input").shouldHave(Condition.exactValue("Санкт-Петербург"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").setValue("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] [type=button]").click();
        $(".calendar-input__calendar-wrapper").shouldBe(Condition.visible);
        if((LocalDate.now().plusDays(7)).getMonthValue() != (LocalDate.now()).getMonthValue()) {
            $("[role=button][data-step='1']").click();
        }
        $(byText(String.valueOf(LocalDate.now().plusDays(7).getDayOfMonth()))).click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
