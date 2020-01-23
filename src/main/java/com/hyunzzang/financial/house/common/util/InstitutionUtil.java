package com.hyunzzang.financial.house.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstitutionUtil {
    private static final String EXCLUSION_PATTERN = "[\\(0-9]";

    private InstitutionUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String exclusionName(String val) {
        Matcher matcher = Pattern.compile(EXCLUSION_PATTERN).matcher(val);

        return matcher.find() ? val.substring(0, matcher.start()) : val;
    }
}
