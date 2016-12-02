package ru.yurivan.selenium.litecart.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("Creating of StringUtils instances is denied.");
    }

    /**
     * Create alphabetically sorted copy of specified string list.
     *
     * @param stringList initial list.
     * @return copy of initial string list with string placed in alphabetical order.
     */
    public static List<String> makeAlphabeticallySortedStringListCopy(final List<String> stringList) {
        List<String> alphabeticallySortedStringList = new ArrayList<>(stringList);
        Collections.sort(alphabeticallySortedStringList, String.CASE_INSENSITIVE_ORDER);
        return alphabeticallySortedStringList;
    }
}
