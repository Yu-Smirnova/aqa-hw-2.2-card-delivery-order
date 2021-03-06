package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryOrderWithComplexElementTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

    @Test
    void shouldSentCardOrderWithAllDataAndComplexElements() {
        Selenide.open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("са");
        $(".input__popup").shouldBe(Condition.visible);
        $(byText("Санкт-Петербург")).click();
        $("[data-test-id=city] input").shouldHave(Condition.exactValue("Санкт-Петербург"));
        $("[data-test-id=date] input").doubleClick().sendKeys("{KEY_BKSP}");
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
        $("[data-test-id=notification] .notification__content")
                .should(Condition.have(Condition.text(formatter.format(LocalDate.now().plusDays(7)))))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
