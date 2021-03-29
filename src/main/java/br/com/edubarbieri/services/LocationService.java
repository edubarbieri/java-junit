package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.addDays;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.edubarbieri.daos.LocationDAO;
import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.exceptions.LocationException;
import br.com.edubarbieri.exceptions.MovieWithoutStockException;
import br.com.edubarbieri.utils.DataUtils;

public class LocationService {
	
	private LocationDAO dao;
	
	private SPCService spcService;
	
	private EmailService emailService;
	
	public Location locateMovie(User user, List<Movie> movies) throws LocationException, MovieWithoutStockException {
	    if(user == null) {
	    	throw new LocationException("Empty user");
	    }
	    
	    
	    boolean negativeUser = false;
		try {
			negativeUser = spcService.negativeUser(user);
		} catch (Exception e) {
			throw new LocationException("Could not retry SPC data");
		}
	    
	    
	    if(negativeUser) {
	    	throw new LocationException("Usuário negativado");
	    }
	    
	    if(movies == null || movies.isEmpty()) {
	    	throw new LocationException("Empty movie");
	    }
	    
	    
	    for (Movie movie : movies) {
	    	if(movie.getStock() == 0) {
	    		throw new MovieWithoutStockException(String.format("Filme %s sem estoque", movie.getName()));
	    	}
			
		}
	    
	    
		Location location = new Location();
		location.setMovies(movies);
		location.setUser(user);
		location.setLocationDate(new Date());
		
		Double total = 0d;
		for (int i = 0; i < movies.size(); i++) {
			Movie m = movies.get(i);
			
			if((i + 1) % 6 == 0) {
				continue;
			}
			if((i + 1) % 5 == 0) {
				total += m.getLocationPrice() - ((m.getLocationPrice() * 75) / 100);
			} else if((i + 1) % 4 == 0) {
				total += m.getLocationPrice() - ((m.getLocationPrice() * 50) / 100);
			} else if((i + 1) % 3 == 0) {
				total += m.getLocationPrice() - ((m.getLocationPrice() * 25) / 100);
			} else {
				total += m.getLocationPrice();
			}
			
		}
		
		
		location.setValue(total);

		//Entrega no dia seguinte
		Date returnDate = new Date();
		returnDate = addDays(returnDate, 1);
		if(DataUtils.dayOfWeek(returnDate, Calendar.SUNDAY)) {
			returnDate = addDays(returnDate, 1);
		}
		
		location.setReturnDate(returnDate);
		
		dao.save(location);
		
		return location;
	}
	
	public void notifyOverdueLocation() {
		List<Location> locations = dao.getOverdueLocations();
		for (Location location : locations) {
			if(location.getReturnDate().before(new Date())) {
				emailService.notifyOverdue(location.getUser());
			}
		}
	}
	
	public void extendLocation(Location location, int dias) {
		Location newLocation = new Location();
		newLocation.setUser(location.getUser());
		newLocation.setMovies(location.getMovies());
		newLocation.setLocationDate(new Date());
		newLocation.setReturnDate(DataUtils.getDateWithMoreDays(dias));
		newLocation.setValue(location.getValue() * dias);
		dao.save(newLocation);
	}


}