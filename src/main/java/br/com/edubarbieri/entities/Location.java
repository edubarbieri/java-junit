package br.com.edubarbieri.entities;

import java.util.Date;

public class Location {

	private User user;
	private Movie movie;
	private Date locationDate;
	private Date returnDate;
	private Double value;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Date getLocationDate() {
        return locationDate;
    }
    public void setLocationDate(Date locationDate) {
        this.locationDate = locationDate;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
	
	
}