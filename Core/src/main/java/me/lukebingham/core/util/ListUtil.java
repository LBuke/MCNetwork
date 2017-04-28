package me.lukebingham.core.util;

import java.util.List;

/**
 * Created by LukeBingham on 19/04/2017.
 */
public class ListUtil {

    public static <T> T addAndReturn(T t, List<T> list) {
        list.add(t);
        return t;
    }
}
