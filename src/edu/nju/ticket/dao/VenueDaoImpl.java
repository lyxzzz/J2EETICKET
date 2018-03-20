package edu.nju.ticket.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.VenueColumn;
import edu.nju.ticket.model.Venue;

@Repository
public class VenueDaoImpl implements VenueDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Venue> findVenue(VenueColumn column, String value) {
		String sql="select * from venue where "+column.name()+"='"+value+"'";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<Venue> result=new ArrayList<Venue>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new Venue(list.get(i)));
		}
		return result;
	}

	@Override
	public void deleteVenue(String id) {
		String sql = "delete from venue where venueid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, id);
			}
		});
	}

	@Override
	public void updateVenue(Venue venue) {
		String sql = "update venue set venuename=?,location=? where venueid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, venue.getVenuename());
				ps.setString(2,venue.getLocation());
				ps.setString(4, venue.getVenueid());
			}
		});
	}

	@Override
	public void createVenue(Venue venue) {
		String sql = "insert into venue(venueid,venuename,location,seatpath,yardid) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, venue.getVenueid());
                ps.setString(2, venue.getVenuename());
                ps.setString(3, venue.getLocation());
                ps.setString(4, venue.getSeatinfopath());
                ps.setString(4, venue.getYardid());
			}
		});
	}

	@Override
	public boolean hasUUID(String id) {
		boolean result=true;
		String sql="select venueid from venue where venueid=?";
		try
		{
			jdbcTemplate.queryForObject(sql, String.class,id);
		}catch(EmptyResultDataAccessException e)
		{
			result=false;
		}
		return result;
	}
}
