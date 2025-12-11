package com.example.changesForKCK;

import static com.example.changesForKCK.WeekScheduleService.buildDaySchedule;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangesService {

    private final FileService fileService;

    private final DateService dateService;

    private final WeekScheduleService weekScheduleService;

    public static String actualChanges;

    static String todayDay = null;

    public String saveChangesForKCK() {
        try {
            fileService.downloadPdfFileFromUrl();

            String stringPdfFile = fileService.parsePdf();

            WeekScheduleService schedule = weekScheduleService.buildSchedule();

            List<String> groupKsk = findChanges(stringPdfFile);

            List<Change> changes = toChanges(groupKsk);

            LocalDate date = dateService.extractDateFromPdf(stringPdfFile);
            todayDay = dateService.getDayByDate(date);
            String weekType = dateService.detectWeekType(stringPdfFile);

            Map<Integer, PairSchedule> todayPairs = schedule.getDay(todayDay);

            applyChanges(todayPairs, changes);

            List<String> todaySchedule = buildDaySchedule(schedule, todayDay, weekType);

            StringBuilder builder = new StringBuilder();
            builder.append("🆕 ЗАМЕНЫ НА ").append(date).append("\n\n");

            for (String line : todaySchedule) {
                builder.append(line).append("\n");
            }
            actualChanges = builder.toString();
            return builder.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> findChanges(String textFromPdf) {
        String[] strings = textFromPdf.split("\n");
        ArrayList<String> arrayList = new ArrayList<>();
        boolean collecting = false;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].startsWith("КСК 22-1")) {
                collecting = true;
            }
            if (collecting) {

                if (strings[i].startsWith("КСК") && !strings[i].startsWith("КСК 22-1")) {
                    collecting = false;
                    continue;
                }

                if (hasTwoInitials(strings[i]) && strings[i].startsWith("КСК 22-1")) {
                    arrayList.add(strings[i].substring(8));
                }
                if (hasTwoInitials(strings[i]) && !strings[i].startsWith("КСК 22-1")) {
                    arrayList.add(strings[i]);
                }
            }

        }

        return arrayList;
    }

    public static List<Change> toChanges(List<String> lines) {
        List<Change> list = new ArrayList<>();

        for (String line : lines) {

            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("КСК")) {
                continue;
            }

            Change change = parseChange(line);
            list.add(change);
        }

        return list;
    }

    public static void applyChanges(Map<Integer, PairSchedule> day, List<Change> changes) {

        for (Change c : changes) {

            if (c.getType().equals("replace")) {
                PairSchedule p = day.get(c.getPair());
                if (p != null) {
                    p.setCommonTeacher(c.getNewTeacher());
                }
            }

            if (c.getType().equals("transfer")) {

                PairSchedule from = day.get(c.getFromPair());
                if (from != null) {
                    from.setCommonTeacher("— отдых —");
                }

                PairSchedule to = day.get(c.getToPair());
                if (to == null) {
                    to = new PairSchedule();
                    day.put(c.getToPair(), to);
                }

                addLesson(to, c, todayDay );
            }
        }
    }

    private static void addLesson(PairSchedule to, Change c, String day) {
        to.setCommonTeacher(c.getNewTeacher());
        if (day.equals("monday")) {
            to.setTime(PairTimes.getMondayTime(c.getToPair()));
        } else if (day.equals("thursday")) {
            to.setTime(PairTimes.getThursdayTime(c.getToPair()));
        } else {
            to.setTime(PairTimes.getTueWedFriTime(c.getToPair()));
        }

    }

    private static boolean hasTwoInitials(String line) {
        int count = 0;

        for (String word : line.split(" ")) {
            if (word.matches("[А-ЯЁ][.][А-ЯЁ][.]")) {
                count++;
            }
        }

        return count >= 2;
    }

    public static Change parseChange(String line) {

        if (line.startsWith("с ")) {
            return parseTransfer(line);
        }

        return parseReplace(line);
    }

    public static Change parseReplace(String line) {
        Change change = new Change();
        String[] strings = line.split(" ");

        change.setPair(Integer.parseInt(strings[0]));

        change.setOldTeacher(strings[1] + " " + strings[2]);
        change.setNewTeacher(strings[3] + " " + strings[4]);

        change.setType("replace");
        return change;
    }

    public static Change parseTransfer(String line) {
        Change change = new Change();
        String[] strings = line.split(" ");
        change.setFromPair(Integer.parseInt(strings[1]));
        change.setToPair(Integer.parseInt(strings[3]));
        change.setOldTeacher(strings[4] + " " + strings[5]);
        change.setNewTeacher(strings[6] + " " + strings[7]);
        change.setType("transfer");
        return change;
    }

}
