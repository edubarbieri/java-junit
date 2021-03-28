package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.addDays;

import java.util.Date;
import java.util.List;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.exceptions.LocationException;
import br.com.edubarbieri.exceptions.MovieWithoutStockException;

public class LocationService {
	
	public Location locateMovie(User user, List<Movie> movies) throws LocationException, MovieWithoutStockException {
	    if(user == null) {
	    	throw new LocationException("Empty user");
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
		Double total = movies.stream().reduce(0.0, (subtotal, m) -> subtotal + m.getLocationPrice(), Double::sum);
		
		location.setValue(total);

		//Entrega no dia seguinte
		Date returnDate = new Date();
		returnDate = addDays(returnDate, 1);
		location.setReturnDate(returnDate);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return location;
	}

}