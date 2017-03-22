package me.lukebingham.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LukeBingham on 22/03/2017.
 */
public class StringUtil {

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
}
