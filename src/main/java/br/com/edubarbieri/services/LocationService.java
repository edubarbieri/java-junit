package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.addDays;

import java.util.Date;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.utils.DataUtils;

public class LocationService {
	
	public Location alugarFilme(User user, Movie movie) {
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
	
	public static void main(String[] args) {
		//cenario
		LocationService service = new LocationService();
		User usuario = new User("Usuario 1");
		Movie filme = new Movie("Filme 1", 2, 5.0);
		
		//acao
		Location locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		System.out.println(locacao.getValue() == 5.0);
		System.out.println(DataUtils.isSameDate(locacao.getLocationDate(), new Date()));
		System.out.println(DataUtils.isSameDate(locacao.getReturnDate(), DataUtils.getDateWithMoreDays(1)));
	}
}