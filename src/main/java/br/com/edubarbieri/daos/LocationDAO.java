package br.com.edubarbieri.daos;

import java.util.List;

import br.com.edubarbieri.entities.Location;

public interface LocationDAO {

	public void save(Location location);

	public List<Location> getOverdueLocations();
}
