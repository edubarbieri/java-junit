package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.addDays;

import java.util.Date;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;

public class LocationService {
	
	public Location locateMovie(User user, Movie movie) throws Exception {
	    
	    if(movie.getStock() == 0) {
	        throw new Exception("Filme sem estoque");
	    }
		Location location = new Location();
		location.setMovie(movie);
		location.setUser(user);
		location.setLocationDate(new Date());
		location.setValue(movie.getLocationPrice());

		//Entrega no dia seguinte
		Date returnDate = new Date();
		returnDate = addDays(returnDate, 1);
		location.setReturnDate(returnDate);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return location;
	}

}