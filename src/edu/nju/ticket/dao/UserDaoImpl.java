package edu.nju.ticket.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.UserColumn;
import edu.nju.ticket.model.User;
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public User findUser(UserColumn column,String value) {
		// TODO Auto-generated method stub
		String sql="select * from user where "+column.name()+"=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);  
		User result;
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
	public void createUser(User user) {
		String sql = "insert into user(userid,useremail,password,createtime) values(?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getUserid());
                ps.setString(2, user.getUseremail());
                ps.setString(3, user.getPassword());
                ps.setString(4, df.format(user.getCreatetime()));
			}
		});
	}
	
	@Override
	public boolean hasUser(String email)
	{
		boolean result=true;
		String sql="select useremail from user where useremail=?";
		try
		{
			jdbcTemplate.queryForObject(sql, String.class,email);
		}catch(EmptyResultDataAccessException e)
		{
			result=false;
		}
		return result;
	}

	@Override
	public boolean hasUUID(String id) {
		boolean result=true;
		String sql="select userid from user where userid=?";
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
	public void deleteUser(User user) {
		String sql = "delete from user where userid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getUserid());
			}
		});
	}

	@Override
	public void updateUser(User user) {
		String sql = "update user set password=?,integral=?,experience=?,activated=? where userid=?";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getPassword());
                ps.setInt(2, user.getIntegral());
                ps.setInt(3, user.getExperience());
                ps.setBoolean(4, user.isActivated());
                ps.setString(5, user.getUserid());
			}
		});
	}

	@Override
	public void updateUsers(List<User> userlist) {
		String sql="update user set integral=integral+?,experience=experience+? where userid=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return userlist.size();   
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, userlist.get(i).getIntegral());
				ps.setInt(2, userlist.get(i).getExperience());
				ps.setString(3, userlist.get(i).getUserid());
			}
		});
	}
}
