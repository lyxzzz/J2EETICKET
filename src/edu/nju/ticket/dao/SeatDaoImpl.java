package edu.nju.ticket.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.PlanSeatInfo;
import edu.nju.ticket.model.SeatInfo;
import edu.nju.ticket.model.SeatState;
import edu.nju.ticket.model.Venue;

@Repository
public class SeatDaoImpl implements SeatDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String path="/seatinfo/";
	
	private static HashMap<String,SeatInfo> seatinfo=new HashMap<String,SeatInfo>();
	
	private static HashMap<String,PlanSeatInfo> planseatinfo=new HashMap<String,PlanSeatInfo>();
	
	@Override
	public void saveSeatInfo(Venue venue) {
		try {
			String infopath=path+venue.getVenueid();
			BufferedWriter bw=new BufferedWriter(new FileWriter(infopath));
			bw.write(venue.getSeatinfo().toString());
			bw.close();
			venue.setSeatinfopath(infopath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadSeatInfo(Venue venue) {
		if(seatinfo.containsKey(venue.getVenueid()))
		{
			venue.setSeatinfo(seatinfo.get(venue.getVenueid()));
		}
		else
		{
			SeatInfo info=new SeatInfo(venue.getSeatinfopath());
			venue.setSeatinfo(info);
			seatinfo.put(venue.getVenueid(), info);
		}
	}

	@Override
	public void loadPlanSeatInfo(SeatInfo info,Plan plan) {
		// TODO Auto-generated method stub
		if(planseatinfo.containsKey(plan.getPlanid()))
		{
			plan.setState(planseatinfo.get(plan.getPlanid()));
		}
		else
		{
			String sql="select * from seat where planid='"+plan.getPlanid()+"'";
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			plan.flushSeatInfo(info);
			for(int i=0;i<list.size();i++)
			{
				plan.pushSeat(list.get(i));
			}
			planseatinfo.put(plan.getPlanid(), plan.getState());
		}
	}
	
	@Override
	public void loadPlanSeatInfo(Plan plan) {
		SeatInfo info;
		if(seatinfo.containsKey(plan.getVenueid()))
		{
			info=seatinfo.get(plan.getVenueid());
		}
		else
		{
			info=new SeatInfo(plan.getSeatinfopath());
			seatinfo.put(plan.getVenueid(), info);
		}
		// TODO Auto-generated method stub
		if(planseatinfo.containsKey(plan.getPlanid()))
		{
			plan.setState(planseatinfo.get(plan.getPlanid()));
		}
		else
		{
			String sql="select * from seat where planid='"+plan.getPlanid()+"'";
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			plan.flushSeatInfo(info);
			for(int i=0;i<list.size();i++)
			{
				plan.pushSeat(list.get(i));
			}
			planseatinfo.put(plan.getPlanid(), plan.getState());
		}
	}

	@Override
	public void createSeats(Plan plan) {
		PlanSeatInfo info=plan.getState();
		String sql = "INSERT INTO seat (planid, seatplace, state, type, price) VALUES (?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return info.size();   
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, info.get(i).getPlanid());
				ps.setString(2, info.get(i).getSeatplace());
				ps.setBoolean(3, info.get(i).getState());
				ps.setInt(1, info.get(i).getType());
				ps.setDouble(1, info.get(i).getPrice());
			}
		});
	}

	@Override
	public void chooseSeats(String planid, List<SeatState> info) {
		// TODO Auto-generated method stub
		String sql="update seat set state=? where planid='"+planid+"' and seatplace=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return info.size();   
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setBoolean(1, info.get(i).getState());
				ps.setString(2, info.get(i).getSeatplace());
			}
		});
		if(planseatinfo.containsKey(planid))
		{
			PlanSeatInfo psi=planseatinfo.get(planid);
			for(int i=0;i<info.size();i++)
			{
				psi.chooseSeat(info.get(i));
			}
		}
	}

	@Override
	public void addUnAssuredSeats(Plan plan,List<Order> orderlist) {
		this.loadPlanSeatInfo(plan);
		PlanSeatInfo info=plan.getState();
		info.pushUnassuredorderlist(orderlist);
	}
	
	@Override
	public List<Order> distributeSeats(Plan plan)
	{
		this.loadPlanSeatInfo(plan);
		PlanSeatInfo seatinfo=plan.getState();
		return seatinfo.distributeSeats();
	}
	
	@Override
	public void distributeSeat(Plan plan,Order order)
	{
		PlanSeatInfo seatinfo=plan.getState();
		seatinfo.distributeSeat(order);
	}
}
