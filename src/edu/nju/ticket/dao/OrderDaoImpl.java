package edu.nju.ticket.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.nju.ticket.common.OrderColumn;
import edu.nju.ticket.common.OrderState;
import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.PlanSeatInfo;
import edu.nju.ticket.model.User;
@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<Order> findOrder(List<OrderColumn> column, List<String> value) {
		String sql="select * from order where ";
		for(int i=0;i<column.size();i++)
		{
			sql=sql+column.get(i).name() + "='"+value.get(i)+"' and ";
		}
		sql=sql+"'1'='1'";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<Order> result=new ArrayList<Order>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new Order(list.get(i)));
		}
		return result;
	}

	@Override
	public void updateOrder(List<Order> order) {
		String sql="update order set state=?,seatplace=? where orderid=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return order.size();   
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, order.get(i).getState());
				ps.setString(2, order.get(i).getSeatplace());
				ps.setString(3, order.get(i).getOrderid());
			}
		});
	}

	private boolean hasUUID(String id) {
		boolean result=true;
		String sql="select orderid from order where orderid=?";
		try
		{
			jdbcTemplate.queryForObject(sql, String.class,id);
		}catch(EmptyResultDataAccessException e)
		{
			result=false;
		}
		return result;
	}
	
	private String getUUID()
	{
		String uuid="";
		do
		{
			uuid=UUID.randomUUID().toString().replace("-", "");
		}while(hasUUID(uuid));
		return uuid;
	}
	
	@Override
	public void createOrder(List<Order> order) {
		String sql = "INSERT INTO order (orderid,userid,planid,createtime,price,coupon, seatplace, state,seattype) VALUES (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				return order.size();   
			}

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, getUUID());
				ps.setString(2, order.get(i).getUserid());
				ps.setString(3, order.get(i).getPlanid());
				ps.setString(4, df.format(order.get(i).getCreatetime()));
				ps.setDouble(5, order.get(i).getPrice());
				ps.setDouble(6, order.get(i).getCoupon());
				ps.setString(7, order.get(i).getSeatplace());
				ps.setInt(8, order.get(i).getState());
				ps.setInt(9, order.get(i).getSeattype());
			}
		});
	}

	@Override
	public void loadUnAssuredOrder(String planid,PlanSeatInfo seatinfo) {
		String sql="select * from order where planid = '"+planid+"' and state =" + OrderState.undistributed.toInt() + " order by createtime asc";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		for(int i=0;i<list.size();i++)
		{
			seatinfo.getUnassuredorderlist().add(new Order(list.get(i)));
		}
	}

	@Override
	public List<User> settleOrder(Plan plan) {
		String sql="select userid,sum(price) as sums from order where planid= '"+plan.getPlanid()+"' and state = "+OrderState.distributed.toInt()+"group by userid";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		List<User> result=new ArrayList<User>();
		for(int i=0;i<list.size();i++)
		{
			result.add(new User((String)list.get(i).get("userid"),(int)list.get(i).get("sums")));
		}
		return result;
	}
	
	
}
