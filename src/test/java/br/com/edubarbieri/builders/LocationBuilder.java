package br.com.edubarbieri.builders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.utils.DataUtils;

public class LocationBuilder {

	private Location location;
	
	private LocationBuilder() {}
	
	public static LocationBuilder oneLocation() {
		LocationBuilder builder = new LocationBuilder();
		builder.location = new Location();
		builder.location.setLocationDate(new Date());
		builder.location.setReturnDate(DataUtils.getDateWithMoreDays(2));
		builder.location.setMovies(MovieBuilder.oneMovie().buildAsList());
		builder.location.setValue(10.0);
		return builder;
	}
	
	public LocationBuilder withReturnDate(Date returnDate) {
		location.setReturnDate(returnDate);
		return this;
	}
	public LocationBuilder withUser(User user) {
		location.setUser(user);
		return this;
	}
	
	public LocationBuilder inOverdue() {
		location.setLocationDate(DataUtils.getDateWithMoreDays(-4));
		location.setReturnDate(DataUtils.getDateWithMoreDays(-2));
		return this;
	}
	
	public Location build() {
		return location;
	}
	
	
	
	public List<Location> buildAsList() {
		return Arrays.asList(location);
	}
	
}
