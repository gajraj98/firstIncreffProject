package com.increff.employee.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderPojo;

@Repository
@Transactional
public class OrderDao extends AbstractDao{

	private static String select_time = "select p from OrderPojo p where time=:time";
	private static String select_id = "select p from OrderPojo p where id=:id";
	private static String select_all = "select p from OrderPojo p";
	private static String select_ByDate = "select p from OrderPojo p where time >= :start and time <= :end";
	private static String delete_id = "delete from OrderPojo p where id=:id";
    
	public void insert(OrderPojo p)
	{
		em().persist(p);
	}
	public void delete(int id)
	{
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	public OrderPojo select(String time) {
		TypedQuery<OrderPojo> query = getQuery(select_time,OrderPojo.class); 
		query.setParameter("time", time);
		return getSingle(query);
	}
	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(select_id,OrderPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public List<OrderPojo> selectByDate(LocalDateTime start , LocalDateTime end){
		TypedQuery<OrderPojo> query = getQuery(select_ByDate,OrderPojo.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return query.getResultList();
	}
	public List<OrderPojo> selectAll()
	{
		TypedQuery<OrderPojo> query = getQuery(select_all,OrderPojo.class); 
		return query.getResultList();
	}
}
