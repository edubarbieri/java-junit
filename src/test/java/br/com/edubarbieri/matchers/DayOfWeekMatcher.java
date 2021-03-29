package br.com.edubarbieri.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.edubarbieri.utils.DataUtils;

public class DayOfWeekMatcher extends TypeSafeMatcher<Date> {

	private int dayOfWeek;
	
	public DayOfWeekMatcher(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	@Override
	public void describeTo(Description description) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		String displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
		description.appendText(displayName);
	}

	@Override
	protected boolean matchesSafely(Date date) {
		return DataUtils.dayOfWeek(date, dayOfWeek);
	}

}
