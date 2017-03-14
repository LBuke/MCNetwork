package me.lukebingham.core.util;

import java.util.regex.Pattern;

/**
 * Created by LukeBingham on 22/02/2017.
 */
public class C {
    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

    public static final String BOLD = COLOR_CHAR + "l";
    public static final String STRIKE = COLOR_CHAR + "m";
    public static final String UNDERLINE = COLOR_CHAR + "n";
    public static final String MAGIC = COLOR_CHAR + "k";
    public static final String ITALIC = COLOR_CHAR + "o";
    public static final String RESET = COLOR_CHAR + "r";

    public static final String BLACK = COLOR_CHAR + "0";
    public static final String DARK_BLUE = COLOR_CHAR + "1";
    public static final String DARK_GREEN = COLOR_CHAR + "2";
    public static final String DARK_AQUA = COLOR_CHAR + "3";
    public static final String DARK_RED = COLOR_CHAR + "4";
    public static final String DARK_PURPLE = COLOR_CHAR + "5";
    public static final String GOLD = COLOR_CHAR + "6";
    public static final String GRAY = COLOR_CHAR + "7";
    public static final String DARK_GRAY = COLOR_CHAR + "8";
    public static final String BLUE = COLOR_CHAR + "9";
    public static final String GREEN = COLOR_CHAR + "a";
    public static final String AQUA = COLOR_CHAR + "b";
    public static final String RED = COLOR_CHAR + "c";
    public static final String LIGHT_PURPLE = COLOR_CHAR + "d";
    public static final String YELLOW = COLOR_CHAR + "e";
    public static final String WHITE = COLOR_CHAR + "f";

    //Key colors
    public static final String DATABASE = YELLOW;


    public static String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String replaceColors(String colorless) {
        colorless = colorless.replaceAll("&([0-9]|[abcdefkrlmnoABCDEFKRLMNO])", COLOR_CHAR + "$1");
        return colorless;
    }

    public static boolean isNumeral(String color) {
        Pattern pattern = Pattern.compile("^(" + COLOR_CHAR + ")\\d$");
        return pattern.matcher(color).find();
    }
}
