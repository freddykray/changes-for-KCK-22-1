package com.example.changesForKCK;

public class PairTimes {

    public static PairTime getMondayTime(int pair) {
        return switch (pair) {
            case 1 -> new PairTime("08:00", "09:30");
            case 2 -> new PairTime("10:20", "11:50");
            case 3 -> new PairTime("12:20", "13:50");
            case 4 -> new PairTime("14:00", "15:30");
            case 5 -> new PairTime("15:40", "17:10");
            case 6 -> new PairTime("17:20", "18:50");
            default -> null;
        };
    }

    public static PairTime getTueWedFriTime(int pair) {
        return switch (pair) {
            case 1 -> new PairTime("08:00", "09:30");
            case 2 -> new PairTime("09:40", "11:10");
            case 3 -> new PairTime("11:40", "13:10");
            case 4 -> new PairTime("13:30", "15:00");
            case 5 -> new PairTime("15:10", "16:40");
            case 6 -> new PairTime("16:50", "18:20");
            case 7 -> new PairTime("18:30", "20:00");
            default -> null;
        };
    }

    public static PairTime getThursdayTime(int pair) {
        return switch (pair) {
            case 1 -> new PairTime("08:00", "09:30");
            case 2 -> new PairTime("09:40", "11:10");
            case 3 -> new PairTime("11:40", "13:10");
            case 4 -> new PairTime("13:30", "14:10");
            case 5 -> new PairTime("14:20", "15:50");
            case 6 -> new PairTime("16:00", "17:30");
            case 7 -> new PairTime("17:40", "19:10");
            default -> null;
        };
    }
}
