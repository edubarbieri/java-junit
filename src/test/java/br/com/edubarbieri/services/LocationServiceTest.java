package br.com.edubarbieri.services;

import static br.com.edubarbieri.builders.LocationBuilder.oneLocation;
import static br.com.edubarbieri.builders.MovieBuilder.oneMovie;
import static br.com.edubarbieri.builders.MovieBuilder.oneMovieWithOutStock;
import static br.com.edubarbieri.builders.UserBuilder.oneUser;
import static br.com.edubarbieri.matchers.CustomMatchers.isMonday;
import static br.com.edubarbieri.matchers.CustomMatchers.isToday;
import static br.com.edubarbieri.matchers.CustomMatchers.todayWithDaysDiff;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.edubarbieri.daos.LocationDAO;
import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;
import br.com.edubarbieri.exceptions.LocationException;
import br.com.edubarbieri.exceptions.MovieWithoutStockException;
import br.com.edubarbieri.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocationService.class, DataUtils.class})
@PowerMockIgnore("jdk.internal.reflect.*")
public class LocationServiceTest {
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public LocationService service;
	
	@Mock
	public LocationDAO locationDao;
	
	@Mock
	public SPCService spcService;
	
	@Mock
	private EmailService emailService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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
	public void shouldLocationWithSuccess() throws Exception {
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.getDate(24, 3, 2021));

		User usuario = oneUser().build();
		List<Movie> movies = oneMovie().buildAsList();

		// acao
		Location locacao = service.locateMovie(usuario, movies);

		// verificacao
		error.checkThat(locacao.getValue(), is(equalTo(5.0)));
		error.checkThat(locacao.getLocationDate(), isToday());
		error.checkThat(locacao.getReturnDate(), todayWithDaysDiff(1));
	}

	@Test(expected = MovieWithoutStockException.class)
	public void shouldNotLocateMovieWithoutStock() throws Exception {
		User usuario = oneUser().build();
		List<Movie> movies = oneMovieWithOutStock().buildAsList();

		service.locateMovie(usuario, movies);
	}

	@Test()
	public void shouldNotLocateMovieWithoutStock2() {
		User usuario = oneUser().build();
		List<Movie> movies = oneMovieWithOutStock().withName("Filme 1").buildAsList();
		try {
			service.locateMovie(usuario, movies);
			fail("Deveria ter dado um erro ");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme Filme 1 sem estoque"));
		}
	}

	@Test()
	public void shouldNotLocateMovieWithoutStock3() throws Exception {
		User usuario = oneUser().build();
		List<Movie> movies = oneMovieWithOutStock().buildAsList();
		
		exception.expect(Exception.class);

		service.locateMovie(usuario, movies);
	}

	@Test
	public void shouldNotLocateMovieWithEmptyUser() throws MovieWithoutStockException {
		LocationService service = new LocationService();
		List<Movie> movies = oneMovie().buildAsList();
		try {
			service.locateMovie(null, movies);
			fail();
		} catch (LocationException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}

	}

	@Test
	public void shouldNotLocateMovieWithEmptyMovie() throws MovieWithoutStockException, LocationException {
		User usuario = oneUser().build();

		exception.expect(LocationException.class);
		exception.expectMessage("Empty movie");

		service.locateMovie(usuario, null);

	}

	@Test
	public void shouldReturnInMondayWhenRentedInSaturday() throws Exception {
		User user = oneUser().withName("Jose Eduardo").build();
		List<Movie> movies = oneMovie().buildAsList();
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.getDate(27, 3, 2021));
		
		Location location = service.locateMovie(user, movies);
		
//		assertThat(location.getReturnDate(), isDayOfWeek(Calendar.SUNDAY));
		assertThat(location.getReturnDate(), isMonday());
		
	}
	@Test
	public void shouldNotLocationMovieToNegativeUser() throws Exception {
		User user = oneUser().build();
		List<Movie> movies = oneMovie().buildAsList();
		when(spcService.negativeUser(any(User.class))).thenReturn(true);
		
		try {
			service.locateMovie(user, movies);
			fail();
		} catch (LocationException e) {
			assertThat(e.getMessage(), is("Usuário negativado"));
		} 
		verify(spcService).negativeUser(user);
	}
	
	@Test
	public void shouldSendEmailToOverdueLocation() {
		User user = oneUser().build();
		User user2 = oneUser().withName("Usuário em dia").build();
		User user3 = oneUser().withName("Outro usuário atrasado").build();
		List<Location> locations = Arrays.asList(
					oneLocation().withUser(user).inOverdue().build(),
					oneLocation().withUser(user2).build(),
					oneLocation().withUser(user3).inOverdue().build(),
					oneLocation().withUser(user3).inOverdue().build()
				);
		when(locationDao.getOverdueLocations()).thenReturn(locations);
		
		service.notifyOverdueLocation();
		verify(emailService, times(3)).notifyOverdue(any(User.class));
		verify(emailService).notifyOverdue(user);
		verify(emailService, atLeastOnce()).notifyOverdue(user3);
		verify(emailService, never()).notifyOverdue(user2);
		verifyNoMoreInteractions(emailService);
		verifyZeroInteractions(spcService);
		
	}
	
	@Test
	public void shouldTreatSPCError() throws Exception {
		User user = oneUser().build();
		List<Movie> movies = oneMovie().buildAsList();
		when(spcService.negativeUser(any(User.class))).thenThrow(new Exception("Could not retry SPC data"));
		
		exception.expect(LocationException.class);
		exception.expectMessage("Could not retry SPC data");
		
		service.locateMovie(user, movies);
	}
	
	@Test
	public void showExtendOneLocation() {
		User user = oneUser().build();
		Location location = oneLocation().withUser(user).build();
		
		service.extendLocation(location, 3);
		
		ArgumentCaptor<Location> argCaptor = ArgumentCaptor.forClass(Location.class);
		verify(locationDao).save(argCaptor.capture());
		Location capturedLocation = argCaptor.getValue();
		
		error.checkThat(capturedLocation.getValue(), is(30.0));
		error.checkThat(capturedLocation.getLocationDate(), isToday());
		error.checkThat(capturedLocation.getReturnDate(), todayWithDaysDiff(3));
		
	}

}
