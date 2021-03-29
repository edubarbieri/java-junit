package br.com.edubarbieri.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.edubarbieri.utils.DataUtils;

public class DateDaysDifferenceMatcher extends TypeSafeMatcher<Date> {

	private int diff;
	
	public DateDaysDifferenceMatcher(int diff) {
		this.diff = diff;
	}
	
	public DateDaysDifferenceMatcher() {
	}

	@Override
	public void describeTo(Description description) {
		Date date = DataUtils.getDateWithMoreDays(diff);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		description.appendText(format.format(date));
	}

	@Override
	protected boolean matchesSafely(Date date) {
		return DataUtils.isSameDate(date, DataUtils.getDateWithMoreDays(diff));
	}

}
