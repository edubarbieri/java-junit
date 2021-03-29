package br.com.edubarbieri.services;

import static br.com.edubarbieri.builders.MovieBuilder.oneMovie;
import static br.com.edubarbieri.builders.UserBuilder.oneUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.edubarbieri.daos.LocationDAO;
import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.exceptions.LocationException;
import br.com.edubarbieri.exceptions.MovieWithoutStockException;

@RunWith(Parameterized.class)
public class CalculationLocationValueTest {

	@Parameter(value = 0)
	public List<Movie> movies;
	
	@Parameter(value = 1)
	public Double expectedValue;
	
	@Parameter(value = 2)
	public String scenario;
	
	@InjectMocks
	private LocationService service;
	
	@Mock
	private LocationDAO locationDao;
	
	@Mock
	private SPCService spcService;
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{generateMovies(2), 8.0, "2 movie = 0%"},
			{generateMovies(3), 11.0, "3 movie = 25%"},
			{generateMovies(4), 13.0, "4 movie = 50%"},
			{generateMovies(5), 14.0, "5 movie = 75%"},
			{generateMovies(6), 14.0, "6 movie = 100%"},
			{generateMovies(7), 18.0, "7 movie = 0%"},
		});
	}
	
	private static List<Movie> generateMovies(int total) {
		List<Movie> movies = new ArrayList<>(total);
		for(int x = 0; x < total; x++) {
			Movie movie = oneMovie()
						.withName("Filme " + (x + 1))
						.withStock(3)
						.withLocationPrice(4.0)
						.build();
			movies.add(movie);
		}
		return movies;
	}
	
	
	@Test
	public void shouldCalculateLocationValueWithDiscounts() throws LocationException, MovieWithoutStockException {
		User user = oneUser().build();

		Location location = service.locateMovie(user, movies);
		assertThat(location.getValue(), is(expectedValue));
	}
}
