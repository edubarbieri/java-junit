package br.com.edubarbieri.entities;

public class Movie {

	private String name;
	private Integer stock;
	private Double locationPrice;  
	
	public Movie() {}
	
	public Movie(String name, Integer stock, Double locationPrice) {
		this.name = name;
		this.stock = stock;
		this.locationPrice = locationPrice;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getLocationPrice() {
        return locationPrice;
    }

    public void setLocationPrice(Double locationPrice) {
        this.locationPrice = locationPrice;
    }
	
	
	
}