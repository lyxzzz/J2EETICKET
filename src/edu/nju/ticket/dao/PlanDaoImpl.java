package edu.nju.ticket.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.PlanColumn;
import edu.nju.ticket.model.Plan;

@Repository
public class PlanDaoImpl implements PlanDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static List<Plan> planlist=null;
	
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@Override
	public List<Plan> findPlan(List<PlanColumn> column, List<String> value) {
		String sql="select * from plan where ";
		for(int i=0;i<column.size();i++)
		{
			sql=sql+column.get(i).name() + "='"+value.get(i)+"' and ";
		}
		sql=sql+"'1'='1' order by startdate asc";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<Plan> result=new ArrayList<Plan>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new Plan(list.get(i)));
		}
		return result;
	}

	@Override
	public void createPlan(Plan plan) {
		String sql = "INSERT INTO plan (planid, yardid, venueid, startdate, enddate, seatinfopath, type, description) VALUES (?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, plan.getPlanid());
                ps.setString(2, plan.getYardid());
                ps.setString(3, plan.getVenueid());
                ps.setString(4, df.format(plan.getStartdate()));
                ps.setString(5, df.format(plan.getEnddate()));
				ps.setString(6, plan.getSeatinfopath());
                ps.setString(7, plan.getType().name());
                ps.setString(8, plan.getDescription());
			}
		});
		this.getPlanList().add(plan);
	}

	@Override
	public boolean hasUUID(String id) {
		boolean result=true;
		String sql="select planid from plan where planid=?";
		try
		{
			jdbcTemplate.queryForObject(sql, String.class,id);
		}catch(EmptyResultDataAccessException e)
		{
			result=false;
		}
		return result;
	}

	@Override
	public void deletePlan(String planid) {
		String sql = "delete from plan where planid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, planid);
			}
		});
	}

	@Override
	public void updatePlan(Plan plan) {
		String sql = "UPDATE plan SET description=?, income=?, completed=? WHERE planid=?;";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, plan.getDescription());
                ps.setDouble(2, plan.getIncome());
                ps.setBoolean(3, plan.isCompleted());
                ps.setString(
                		4, plan.getPlanid());
			}
		});
	}

	@Override
	public List<Plan> getPlanList() {
		if(planlist==null)
		{
			planlist=this.findPlan(new ArrayList<PlanColumn>(), new ArrayList<String>());
		}
		return planlist;
	}

}
