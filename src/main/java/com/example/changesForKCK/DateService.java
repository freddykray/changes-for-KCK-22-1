package com.example.changesForKCK;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    public String detectWeekType(String pdfText) {
        if (pdfText.contains("бел") || pdfText.contains("Бел")) {
            return "white";
        }
        if (pdfText.contains("зел") || pdfText.contains("Зел")) {
            return "green";
        }
        return "white"; // на всякий случай
    }

    public LocalDate extractDateFromPdf(String text) {

        Pattern p = Pattern.compile(
                "(\\d{1,2})\\s+(января|февраля|марта|апреля|мая|июня|июля|августа|сентября|октября|ноября|декабря)");
        Matcher m = p.matcher(text.toLowerCase());

        if (!m.find()) {
            throw new RuntimeException("Дата замен не найдена в PDF");
        }

        int day = Integer.parseInt(m.group(1));

        String monthName = m.group(2);

        Map<String, Integer> months = Map.ofEntries(
                Map.entry("января", 1),
                Map.entry("февраля", 2),
                Map.entry("марта", 3),
                Map.entry("апреля", 4),
                Map.entry("мая", 5),
                Map.entry("июня", 6),
                Map.entry("июля", 7),
                Map.entry("августа", 8),
                Map.entry("сентября", 9),
                Map.entry("октября", 10),
                Map.entry("ноября", 11),
                Map.entry("декабря", 12)
        );

        int month = months.get(monthName);
        int year = LocalDate.now().getYear();

        return LocalDate.of(year, month, day);
    }

    public static String getDayByDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();

        return switch (day) {
            case MONDAY -> "monday";
            case TUESDAY -> "tuesday";
            case WEDNESDAY -> "wednesday";
            case THURSDAY -> "thursday";
            case FRIDAY -> "friday";
            default -> null;
        };
    }

}
