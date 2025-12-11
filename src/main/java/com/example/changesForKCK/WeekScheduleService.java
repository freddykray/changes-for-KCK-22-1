package com.example.changesForKCK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class WeekScheduleService {
    private Map<Integer, PairSchedule> monday;
    private Map<Integer, PairSchedule> tuesday;
    private Map<Integer, PairSchedule> wednesday;
    private Map<Integer, PairSchedule> thursday;
    private Map<Integer, PairSchedule> friday;

    public WeekScheduleService buildSchedule() {
        WeekScheduleService schedule = new WeekScheduleService();
        schedule.setMonday(new HashMap<>());
        schedule.setTuesday(new HashMap<>());
        schedule.setWednesday(new HashMap<>());
        schedule.setThursday(new HashMap<>());
        schedule.setFriday(new HashMap<>());

        // ============================
        // ПОНЕДЕЛЬНИК
        // ============================

        PairSchedule mon1 = new PairSchedule();
        mon1.setCommonTeacher("Толокнеева Н.А.");
        mon1.setTime(PairTimes.getMondayTime(1));
        schedule.getMonday().put(1, mon1);

        PairSchedule mon2 = new PairSchedule();
        mon2.setCommonTeacher("МДК 05.01 — Порядин В.И.");
        mon2.setTime(PairTimes.getMondayTime(2));
        schedule.getMonday().put(2, mon2);

        PairSchedule mon3 = new PairSchedule();
        mon3.setCommonTeacher("МДК 05.01 — Порядин В.И.");
        mon3.setTime(PairTimes.getMondayTime(3));
        schedule.getMonday().put(3, mon3);

        // ============================
        // ВТОРНИК
        // ============================

        PairSchedule tue1 = new PairSchedule();
        tue1.setWhiteTeacher("МДК 05.01 — Порядин В.И.");
        tue1.setGreenTeacher("Программное обеспечение КС и WEB-сервисов — Дашков П.А.");
        tue1.setTime(PairTimes.getTueWedFriTime(1));
        schedule.getTuesday().put(1, tue1);

        PairSchedule tue2 = new PairSchedule();
        tue2.setCommonTeacher("МДК 06.01 — Желтухин А.А.");
        tue2.setTime(PairTimes.getTueWedFriTime(2));
        schedule.getTuesday().put(2, tue2);

        PairSchedule tue3 = new PairSchedule();
        tue3.setCommonTeacher("МДК 05.01 — Порядин В.И.");
        tue3.setTime(PairTimes.getTueWedFriTime(3));
        schedule.getTuesday().put(3, tue3);

        // ============================
        // СРЕДА
        // ============================

        PairSchedule wed1 = new PairSchedule();
        wed1.setCommonTeacher("Охрана труда — Исаева Ю.В.");
        wed1.setTime(PairTimes.getTueWedFriTime(1));
        schedule.getWednesday().put(1, wed1);

        PairSchedule wed2 = new PairSchedule();
        wed2.setCommonTeacher("МДК 06.01 — Желтухин А.А.");
        wed2.setTime(PairTimes.getTueWedFriTime(2));
        schedule.getWednesday().put(2, wed2);

        PairSchedule wed3 = new PairSchedule();
        wed3.setCommonTeacher("ПО КС и WEB — Дашков П.А.");
        wed3.setTime(PairTimes.getTueWedFriTime(3));
        schedule.getWednesday().put(3, wed3);

        PairSchedule wed4 = new PairSchedule();
        wed4.setCommonTeacher("Иностранный язык — Завалишина С.А.");
        wed4.setTime(PairTimes.getTueWedFriTime(4));
        schedule.getWednesday().put(4, wed4);

        // ============================
        // ЧЕТВЕРГ
        // ============================

        PairSchedule thu1 = new PairSchedule();
        thu1.setCommonTeacher("МДК 06.01 — Желтухин А.А.");
        thu1.setTime(PairTimes.getThursdayTime(1));
        schedule.getThursday().put(1, thu1);

        PairSchedule thu2 = new PairSchedule();
        thu2.setCommonTeacher("Физическая культура — Кирюхина Е.И.");
        thu2.setTime(PairTimes.getThursdayTime(2));
        schedule.getThursday().put(2, thu2);

        PairSchedule thu3 = new PairSchedule();
        thu3.setWhiteTeacher("Базы данных — Минеева А.С.");
        thu3.setGreenTeacher("МДК 06.01 — Желтухин А.А.");
        thu3.setTime(PairTimes.getThursdayTime(3));
        schedule.getThursday().put(3, thu3);

        PairSchedule thu4 = new PairSchedule();
        thu4.setCommonTeacher("Классный час");
        thu4.setTime(PairTimes.getThursdayTime(4));
        schedule.getThursday().put(4, thu4);

        PairSchedule thu5 = new PairSchedule();
        thu5.setWhiteTeacher("Правовое обеспечение ПД — Бернер А.В.");
        thu5.setGreenTeacher("Экономика отрасли — Толокнеева Н.А.");
        thu5.setTime(PairTimes.getThursdayTime(5));
        schedule.getThursday().put(5, thu5);

        // ============================
        // ПЯТНИЦА
        // ============================

        PairSchedule fri1 = new PairSchedule();
        fri1.setCommonTeacher("— нет занятия —");
        fri1.setTime(PairTimes.getTueWedFriTime(1));
        schedule.getFriday().put(1, fri1);

        PairSchedule fri2 = new PairSchedule();
        fri2.setCommonTeacher("ПО КС и WEB — Дашков П.А.");
        fri2.setTime(PairTimes.getTueWedFriTime(2));

        schedule.getFriday().put(2, fri2);

        PairSchedule fri3 = new PairSchedule();
        fri3.setCommonTeacher("Базы данных — Минеева А.С.");
        fri3.setTime(PairTimes.getTueWedFriTime(3));

        schedule.getFriday().put(3, fri3);

        PairSchedule fri4 = new PairSchedule();
        fri4.setCommonTeacher("МДК 06.01 — Желтухин А.А.");
        fri4.setTime(PairTimes.getTueWedFriTime(4));

        schedule.getFriday().put(4, fri4);

        PairSchedule fri5 = new PairSchedule();
        fri5.setCommonTeacher("Правовое обеспечение ПД — Бернер А.В.");
        fri5.setTime(PairTimes.getTueWedFriTime(5));

        schedule.getFriday().put(5, fri5);

        return schedule;
    }

    public static String getTeacher(PairSchedule pair, String weekType) {
        if (pair == null) {
            return "— нет пары —";
        }

        if (pair.getCommonTeacher() != null) {
            return pair.getCommonTeacher();
        }

        if (weekType.equals("white") && pair.getWhiteTeacher() != null) {
            return pair.getWhiteTeacher();
        }

        if (weekType.equals("green") && pair.getGreenTeacher() != null) {
            return pair.getGreenTeacher();
        }

        return "— нет занятия —";
    }

    public Map<Integer, PairSchedule> getDay(String day) {
        return switch (day) {
            case "monday" -> this.monday;
            case "tuesday" -> this.tuesday;
            case "wednesday" -> this.wednesday;
            case "thursday" -> this.thursday;
            case "friday" -> this.friday;
            default -> null;
        };
    }

    public static List<String> buildDaySchedule(WeekScheduleService schedule, String day, String weekType){
        Map<Integer, PairSchedule> pairs = switch (day) {
            case "monday" -> schedule.getMonday();
            case "tuesday" -> schedule.getTuesday();
            case "wednesday" -> schedule.getWednesday();
            case "thursday" -> schedule.getThursday();
            case "friday" -> schedule.getFriday();
            default -> throw new IllegalArgumentException("Неизвестный день");
        };

        List<String> result = new ArrayList<>();

        for (int pairNum : pairs.keySet()) {
            PairSchedule pair = pairs.get(pairNum);

            String teacher = getTeacher(pair, weekType);
            PairTime time = pair.getTime();

            String line = pairNum + " пара (" + time.getStart() + " - " + time.getEnd() + "): " + teacher;
            result.add(line);
        }

        return result;

    }

}
