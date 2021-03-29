package br.com.edubarbieri.builders;

import java.util.Arrays;
import java.util.List;

import br.com.edubarbieri.entities.Movie;

public class MovieBuilder {
	
	private Movie movie;
	
	
	private MovieBuilder() {}
	
	public static MovieBuilder oneMovie() {
		MovieBuilder m = new MovieBuilder();
		m.movie = new Movie();
		m.movie.setName("Test movie");
		m.movie.setLocationPrice(5.0);
		m.movie.setStock(2);
		return m;
	}
	public static MovieBuilder oneMovieWithOutStock() {
		MovieBuilder m = new MovieBuilder();
		m.movie = new Movie();
		m.movie.setName("Test movie");
		m.movie.setLocationPrice(5.0);
		m.movie.setStock(0);
		return m;
	}
	
	public MovieBuilder withName(String name) {
		movie.setName(name);
		return this;
	}
	
	public MovieBuilder withLocationPrice(Double price) {
		movie.setLocationPrice(price);
		return this;
	}
	
	public MovieBuilder withOutStock() {
		movie.setStock(0);
		return this;
	}
	
	public MovieBuilder withStock(int stock) {
		movie.setStock(stock);
		return this;
	}
	
	public Movie build() {
		return movie;
	}
	public List<Movie> buildAsList() {
		return Arrays.asList(movie);
	}
	
}
