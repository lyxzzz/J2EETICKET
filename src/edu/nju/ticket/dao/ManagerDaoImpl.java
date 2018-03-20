package edu.nju.ticket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.ManagerColumn;
import edu.nju.ticket.model.Manager;

@Repository
public class ManagerDaoImpl implements ManagerDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Manager findManager(ManagerColumn column, String value) {
		String sql="select * from manager where "+column.name()+"=?";
		RowMapper<Manager> rowMapper = new BeanPropertyRowMapper<>(Manager.class);  
		Manager result;
		try
		{
			result=jdbcTemplate.queryForObject(sql, rowMapper ,value);
		}catch(EmptyResultDataAccessException e)
		{
			result=null;
		}
		return result;
	}

}
