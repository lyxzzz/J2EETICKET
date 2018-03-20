package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.common.VenueColumn;
import edu.nju.ticket.model.Venue;

public interface VenueDao {
	public List<Venue> findVenue(VenueColumn column,String value);
	
	public void deleteVenue(String id);
	
	public void updateVenue(Venue venue);
	
	public void createVenue(Venue venue);
	
	public boolean hasUUID(String id);
}
