package ru.netology;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDeliveryOrderTest {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

    @Test
    void shouldSentCardOrderWithAllData() {
        Selenide.open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").setValue("\b\b\b\b\b\b\b\b");
        $("[data-test-id=date] input").setValue(formatter.format(LocalDate.now().plusDays(3)));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79000000000");
        $("[data-test-id=agreement]").click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.byText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
