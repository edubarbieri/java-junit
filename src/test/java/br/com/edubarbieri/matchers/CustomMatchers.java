package br.com.edubarbieri.matchers;

import java.util.Calendar;

public class CustomMatchers {

	public static DayOfWeekMatcher isMonday() {
		return new DayOfWeekMatcher(Calendar.MONDAY);
	}
	public static DayOfWeekMatcher isDayOfWeek(int dayOfWeek) {
		return new DayOfWeekMatcher(dayOfWeek);
	}
	
	public static DateDaysDifferenceMatcher isToday() {
		return new DateDaysDifferenceMatcher();
	}
	public static DateDaysDifferenceMatcher todayWithDaysDiff(int days) {
		return new DateDaysDifferenceMatcher(days);
	}
}
