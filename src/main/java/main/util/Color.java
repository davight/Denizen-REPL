package main.util;

public enum Color {

    YELLOW("<Y>", "\u001B[33m"),
    ORANGE("<O>", "\u001B[33;1m"),
    DARK_GRAY("<G>", "\u001B[90m"),
    GRAY("<LG>", "\u001B[37m"),
    GREEN("<GR>", "\u001B[32m"),
    AQUA("<A>", "\u001B[36m"),
    DARK_RED("<R>", "\u001B[31m"),
    RED("<LR>", "\u001B[91m"),
    LIGHT_PURPLE("<LP>", "\u001B[35m"),
    WHITE("<W>", "\u001B[37;1m"),
    RESET("<RESET>", "\u001B[0m");

    private final String denizenFormat, ansiFormat;

    Color(String denizenFormat, String ansiFormat) {
        this.denizenFormat = denizenFormat;
        this.ansiFormat = ansiFormat;
    }

    public String getDenizenFormat() {
        return denizenFormat;
    }

    public String getAnsiFormat() {
        return ansiFormat;
    }

    public static String parseDenizenFormat(String string) {
        for (Color color : values()) {
            string = string.replace(color.denizenFormat, color.ansiFormat);
        }
        string = string.replace("<FORCE_ALIGN>", "");
        return string + RESET.ansiFormat;
    }

    public static String resetString(String string) {
        for (Color color : values()) {
            string = string.replace(color.ansiFormat, "");
        }
        return Color.WHITE.ansiFormat + string;
    }

    public static Color getRandomColor() {
        return values()[(int) (Math.random() * values().length - 1)]; // Reset is special case
    }
}
