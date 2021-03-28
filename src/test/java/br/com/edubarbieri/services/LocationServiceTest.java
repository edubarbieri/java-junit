package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.getDateWithMoreDays;
import static br.com.edubarbieri.utils.DataUtils.isSameDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.exceptions.LocationException;
import br.com.edubarbieri.exceptions.MovieWithoutStockException;

public class LocationServiceTest {
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	public LocationService service;
	
	@Before
	public void setup() {
		service = new LocationService();
	}
	
	@After
	public void tearDown() {
	}
	
	@BeforeClass
	public static void setupClass() {
		System.out.println("Before class");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("After class");
	}
	
	@Test
	public void testLocation() throws Exception {
		// cenario

		User usuario = new User("Usuario 1");
		List<Movie> movies = Arrays.asList(new Movie("Filme 1", 10, 5.0));

		// acao
		Location locacao = service.locateMovie(usuario, movies);

		// verificacao
		error.checkThat(locacao.getValue(), is(equalTo(5.0)));
		error.checkThat(isSameDate(locacao.getLocationDate(), new Date()), is(true));
		error.checkThat(isSameDate(locacao.getReturnDate(), getDateWithMoreDays(1)), is(true));
	}

	@Test(expected = MovieWithoutStockException.class)
	public void testLocateMovieWithStock() throws Exception {
		User usuario = new User("Usuario 1");
		List<Movie> movies = Arrays.asList(new Movie("Filme 1", 0, 5.0));

		service.locateMovie(usuario, movies);
	}

	@Test()
	public void testLocateMovieWithStock2() {
		User usuario = new User("Usuario 1");
		List<Movie> movies = Arrays.asList(new Movie("Filme 1", 0, 5.0));
		try {
			service.locateMovie(usuario, movies);
			fail("Deveria ter dado um erro ");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme Filme 1 sem estoque"));
		}
	}

	@Test()
	public void testLocateMovieWithStock3() throws Exception {
		User usuario = new User("Usuario 1");
		List<Movie> movies = Arrays.asList(new Movie("Filme 1", 0, 5.0));

		exception.expect(Exception.class);

		service.locateMovie(usuario, movies);
	}

	@Test
    public void testLocationEmptyUser() throws MovieWithoutStockException {
    	LocationService service = new LocationService();
    	List<Movie> movies = Arrays.asList(new Movie("Filme 1", 0, 5.0));
    	try {
    		service.locateMovie(null, movies);
			fail();
		} catch (LocationException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}
    	
    }
	@Test
	public void testLocationEmptyMovie() throws MovieWithoutStockException, LocationException {
		User usuario = new User("Usuario 1");

		exception.expect(LocationException.class);
		exception.expectMessage("Empty movie");
		
		service.locateMovie(usuario, null);

	}

}
