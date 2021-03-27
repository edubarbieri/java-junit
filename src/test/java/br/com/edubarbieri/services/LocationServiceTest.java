package br.com.edubarbieri.services;

import static br.com.edubarbieri.utils.DataUtils.getDateWithMoreDays;
import static br.com.edubarbieri.utils.DataUtils.isSameDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.edubarbieri.entities.Location;
import br.com.edubarbieri.entities.Movie;
import br.com.edubarbieri.entities.User;

public class LocationServiceTest {
    @Rule
    public ErrorCollector error = new ErrorCollector();
    
    @Test
    public void testLocation() throws Exception {
        //cenario
        LocationService service = new LocationService();
        User usuario = new User("Usuario 1");
        Movie filme = new Movie("Filme 1", 2, 5.0);
        
        //acao
        Location locacao = service.locateMovie(usuario, filme);
        
        //verificacao
        error.checkThat(locacao.getValue(), is(equalTo(5.0)));
        error.checkThat(isSameDate(locacao.getLocationDate(), new Date()), is(true));
        error.checkThat(isSameDate(locacao.getReturnDate(), getDateWithMoreDays(1)), is(true));
    }
    
    @Test(expected = Exception.class)
    public void testLocateMovieWithStock() throws Exception {
        LocationService service = new LocationService();
        User usuario = new User("Usuario 1");
        Movie filme = new Movie("Filme 1", 0, 5.0);
        
        service.locateMovie(usuario, filme);
    }
    
    @Test()
    public void testLocateMovieWithStock2() {
        LocationService service = new LocationService();
        User usuario = new User("Usuario 1");
        Movie filme = new Movie("Filme 1", 0, 5.0);
        try {
            service.locateMovie(usuario, filme);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }
}
