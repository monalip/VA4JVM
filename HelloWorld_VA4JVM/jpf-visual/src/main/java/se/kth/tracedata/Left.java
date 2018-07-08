package se.kth.tracedata;

import java.util.Arrays;

/**
 * left justified output
 * <2do> this is not worth a class! use a general TextFormatter
 */
public class Left {
  public static String format (String value, int spaces) {
    int vlen = value.length();
    int newLen = Math.max(spaces, vlen);
    char[] result = new char[newLen];
    value.getChars(0, vlen, result, 0);
    Arrays.fill(result, vlen, newLen, ' ');
    return new String(result);
  }

  public static String format (int value, int digits) {
    return format(Integer.toString(value),digits);
  }
}
