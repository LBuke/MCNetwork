package me.lukebingham.core.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LukeBingham on 22/03/2017.
 */
public final class StringUtil {

    public static List<String> breakUp(String input, int length) {
        List<String> temp = new ArrayList<>();
        int i = 0;
        String s = "";
        for(String str : input.split(" ")) {
            if(i + str.toCharArray().length >= length) {
                temp.add(s);
                s = str + " ";
                i = 0;
                continue;
            }

            i += str.toCharArray().length;
            s += str + " ";
        }
        temp.add(s);
        return temp;
    }

    public static List<String> breakUp(String input) {
        List<String> temp = new ArrayList<>();
        for(String str : input.split("\n")) {
            temp.add(str);
        }
        return temp;
    }

    public static String getProgress(char symbol, float current, int length, String... color) {
        StringBuilder progressBar = new StringBuilder(color[0]);
        boolean b = false;
        for (int i = 0; i < length; i++) {
            if (!b && (float) i / (float) length >= current) {
                progressBar.append(color[1]);
                b = true;
            }

            progressBar.append(symbol);
        }

        return progressBar.toString();
    }
}
