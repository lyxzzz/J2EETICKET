package edu.nju.ticket.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.YardColumn;
import edu.nju.ticket.model.Yard;

@Repository
public class YardDaoImpl implements YardDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public Yard findYard(YardColumn column, String value) {
		String sql="select * from yard where "+column+"=?";
		RowMapper<Yard> rowMapper = new BeanPropertyRowMapper<>(Yard.class);  
		Yard result;
		try
		{
			result=jdbcTemplate.queryForObject(sql, rowMapper ,value);
		}catch(EmptyResultDataAccessException e)
		{
			result=null;
		}
		return result;
	}

	@Override
	public void createYard(Yard yard) {
		String sql = "insert into yard(yardid,password,yardname,address,email,payacount,createtime) values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, yard.getYardid());
				ps.setString(2, yard.getPassword());
				ps.setString(3,yard.getYardname());
				ps.setString(4, yard.getAddress());
				ps.setString(5, yard.getEmail());
				ps.setString(6, yard.getPayacount());
				ps.setString(7, df.format(yard.getCreatetime()));
			}
		});
	}

	@Override
	public boolean hasUUID(String id) {
		boolean result=true;
		String sql="select yardid from yard where yardid=?";
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
	public void deleteYard(Yard yard) {
		String sql = "delete from yard where yardid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, yard.getYardid());
			}
		});
	}

	@Override
	public void updateYard(Yard yard) {
		String sql = "update yard set yardname=?,address=?,payacount=?,activated=? where yardid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(5, yard.getYardid());
				ps.setString(1,yard.getYardname());
				ps.setString(2, yard.getAddress());
				ps.setString(3, yard.getPayacount());
				ps.setBoolean(4, yard.isActivated());
			}
		});
	}

	@Override
	public List<Yard> findUnCheckedYard() {
		String sql="select * from yard where activated = 0 order by createtime asc";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<Yard> result=new ArrayList<Yard>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new Yard(list.get(i)));
		}
		return result;
	}
	

	@Override
	public List<Yard> findReviseYard() {
		String sql="select * from yardrevise";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<Yard> result=new ArrayList<Yard>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new Yard(list.get(i)));
		}
		return result;
	}

	@Override
	public Yard findRevise(YardColumn column, String value) {
		String sql="select * from yardrevise where "+column+"=?";
		RowMapper<Yard> rowMapper = new BeanPropertyRowMapper<>(Yard.class);  
		Yard result;
		try
		{
			result=jdbcTemplate.queryForObject(sql, rowMapper ,value);
		}catch(EmptyResultDataAccessException e)
		{
			result=null;
		}
		return result;
	}

	@Override
	public void insertRevise(Yard yard) {
		String sql = "insert into yardrevise(yardid,yardname,address,payacount) values(?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, yard.getYardid());
				ps.setString(2,yard.getYardname());
				ps.setString(3, yard.getAddress());
				ps.setString(4, yard.getPayacount());
			}
		});
	}

	@Override
	public void updateRevise(Yard yard) {
		String sql = "update yardrevise set yardname=?,address=?,payacount=? where yardid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(4, yard.getYardid());
				ps.setString(1, yard.getYardname());
				ps.setString(2,yard.getAddress());
				ps.setString(3, yard.getPayacount());
			}
		});
	}

	@Override
	public void deleteRevise(String yardid) {
		String sql = "delete from yardrevise where yardid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, yardid);
			}
		});
	}
}
