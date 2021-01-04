package cuie.Schaellmaa.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;

/**
 * Contains the logic to evaluate and convert the given input time into the correct time format
 */
public final class TimeRegex {

  public static final String FORMATTED_TIME_PATTERN = "HH:mm:ss";
  private static final String OPTS_REGEX = "([1-9]{1}[pl]{1})|(([1-9]|[1-5][0-9]|60)[ms]{1})|(([1-9]|[1][0-9]|[2][0-3]|24)[h]{1})";
  private static final Pattern OPTS_PATTERN = Pattern.compile(OPTS_REGEX, Pattern.CASE_INSENSITIVE);
  private static final String MIN_DIGITS_REGEX = "\\d{3,}";
  private static final Pattern MIN_DIGITS_PATTERN = Pattern
      .compile(MIN_DIGITS_REGEX, Pattern.CASE_INSENSITIVE);
  private static final String WRONG_INPUT_REGEX = "(^\\d*([plhms]{2}|[plhms]{1}[:]|[:][plhms]{1}\\d*|[plhms](\\d+|\\D+)|(\\d[:])*[^plhms:\\d])|^\\D)|\\d*[:]{2}";
  private static final Pattern WRONG_INPUT_PATTERN = Pattern
      .compile(WRONG_INPUT_REGEX, Pattern.CASE_INSENSITIVE);
  private static final String WRONG_HOUR_REGEX = "^(2[4-9]|[3-9][0-9])[:]\\d{2}[:]\\d{2}";
  private static final Pattern WRONG_HOUR_PATTERN = Pattern
      .compile(WRONG_HOUR_REGEX, Pattern.CASE_INSENSITIVE);
  private static final String WRONG_NUMBER_OF_DIGITS_REGEX = "\\d{7}";
  private static final Pattern WRONG_NUMBER_OF_DIGITS_PATTERN = Pattern
      .compile(WRONG_NUMBER_OF_DIGITS_REGEX, Pattern.CASE_INSENSITIVE);
  private static final String TIME_OVER_REGEX = "[0]*[:]*[0]*[:]*[0]*";
  private static final Pattern TIME_OVER_PATTERN = Pattern.compile(TIME_OVER_REGEX);

  private TimeRegex() {
  }

  /**
   * Matches a convertible value option
   *
   * @param inputValue the input value to be evaluated
   * @return A matcher containing the pattern matching results
   */
  public static Matcher matchOptions(String inputValue) {
    return OPTS_PATTERN.matcher(inputValue);
  }

  /**
   * Matches any invalid input
   *
   * @param inputValue the input value to be evaluated
   * @return A matcher containing the pattern matching results
   */
  public static Matcher matchWrongInputs(String inputValue) {
    return WRONG_INPUT_PATTERN.matcher(inputValue);
  }

  /**
   * Matches exceeded number of numbers
   *
   * @param inputValue the input value to be evaluated
   * @return A matcher containing the pattern matching results
   */
  public static Matcher matchExceededDigits(String inputValue) {
    String onlyDigits = inputValue.replaceAll("\\D", "");
    return WRONG_NUMBER_OF_DIGITS_PATTERN.matcher(onlyDigits);
  }

  /**
   * Matches a minimum number of numbers
   *
   * @param inputValue the input value to be evaluated
   * @return A matcher containing the pattern matching results
   */
  public static Matcher matchMinDigits(String inputValue) {
    String onlyDigits = inputValue.replaceAll("\\D", "");
    return MIN_DIGITS_PATTERN.matcher(onlyDigits);
  }

  /**
   * Matches a number of hours greather than 24
   *
   * @param inputValue the input value to be evaluated
   * @return A matcher containing the pattern matching results
   */
  public static Matcher matchWrongHour(String inputValue) {
    return WRONG_HOUR_PATTERN.matcher(inputValue);
  }

  /**
   * @param inputValue
   * @return
   */
  public static String timeColons(String inputValue) {
    return inputValue.replaceAll("\\D+", "")
        .replaceAll("..(?!$)", "$0:");
  }

  /**
   * Converts a valid option into the appropriate time format
   *
   * @param inputValue the input value to be evaluated
   * @return A corresponding time in the required datetime format
   */
  public static String convertOption(String inputValue) {
    int digits = Integer.parseInt(inputValue.replaceAll("\\D", ""));
    String type = inputValue.replaceAll("\\d", "");
    switch (type.toLowerCase()) {
      case "p":
        return optionAsStringTime((15 * 60) * digits);
      case "l":
        return optionAsStringTime((45 * 60) * digits);
      case "h":
        if (digits == 24) {
          digits = 23 * 3600 + 60 * 60 + 60;
        } else {
          digits = digits * 3600;
        }
        return optionAsStringTime(digits);
      case "m":
        return optionAsStringTime((60) * digits);
      case "s":
        return optionAsStringTime(digits);
    }
    return optionAsStringTime(0);
  }

  /**
   * Convert excess time format hours to 23:59:59
   *
   * @param inputValue the input value to be evaluated
   * @return the valid time format as string to be parsed by the LocalTime
   */
  public static String ifWrongHoursConvertTo24HourTimeString(String inputValue) {
    if (TimeRegex.matchWrongHour(inputValue).matches()) {
      return "23:59:59";
    } else {
      return inputValue;
    }
  }

  /**
   * @param totalSeconds
   * @return
   */
  private static String optionAsStringTime(int totalSeconds) {
    int hours = totalSeconds / 3600;
    int minutes = (totalSeconds / 60) % 60;
    int seconds = totalSeconds % 60;
    if (hours == 24) {
      hours = 23;
      minutes = 59;
      seconds = 59;
    }
    String h = String.valueOf(hours);
    String m = String.valueOf(minutes);
    String s = String.valueOf(seconds);
    String hoursAsString = (hours < 10 && hours > 0 ? "0" + h : hours == 0 ? "00" : h);
    String minutesAsString = (minutes < 10 && minutes > 0 ? "0" + m : minutes == 0 ? "00" : m);
    String secondsAsString = (seconds < 10 && seconds > 0 ? "0" + s : seconds == 0 ? "00" : s);
    return hoursAsString + ":" + minutesAsString + ":" + secondsAsString;
  }

  /**
   * converts the inputValue into a correct time format
   *
   * @param inputValue the input value to be evaluated
   * @return
   */
  public static String parseLeftTimeValues(String inputValue) {
    String timeToParse = removeUnnecessaryColons(inputValue);
    if (timeToParse.contains(":")) {
      String[] vals = timeToParse.split(":");
      timeToParse = "";
      for (String val : vals) {
        int num = Integer.parseInt(val);
        num = num >= 60 ? 59 : num;
        timeToParse =
            timeToParse + (num > 0 && num < 10 ? "0" + num : num == 0 ? "00" : "" + num) + ":";
      }
      switch (vals.length) {
        case 1:
          timeToParse = "00:00:" + timeToParse;
        case 2:
          timeToParse = "00:" + timeToParse;
      }
    } else {
      int num = Integer.parseInt(timeToParse);
      timeToParse = "00:00:" + (num > 0 && num < 10 ? "0" + inputValue
          : num == 0 ? "00" : inputValue);
    }
    return removeUnnecessaryColons(timeToParse);
  }

  /**
   * Removes a trailing colon from the input value
   *
   * @param inputValue the input value to be evaluated
   * @return a string without the trailing colon
   */
  public static String removeUnnecessaryColons(String inputValue) {
    if (inputValue != null && inputValue.length() > 0
        && inputValue.charAt(inputValue.length() - 1) == ':') {
      inputValue = inputValue.substring(0, inputValue.length() - 1);
    }
    return inputValue;
  }

  public static Boolean timeIsOver(String inputValue) {
    return TIME_OVER_PATTERN.matcher(inputValue).matches();
  }
}