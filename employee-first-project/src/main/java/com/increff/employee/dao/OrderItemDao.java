package com.increff.employee.dao;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderItemPojo;

import java.util.List;

@Repository
@Transactional
public class OrderItemDao extends AbstractDao{

	private static String select_orderid = "select p from OrderItemPojo p where orderId=:orderId";
	private static String select_Id = "select p from OrderItemPojo p where id=:id";
	private static String delete_OrderId = "delete from OrderItemPojo p where orderId=:orderId";
	private static String delete_id = "delete from OrderItemPojo p where id=:id";

	public void insert(OrderItemPojo p)
	{
		em().persist(p);
	}
	public List<OrderItemPojo> selectAll(int orderId)
	{
		TypedQuery<OrderItemPojo> query = getQuery(select_orderid,OrderItemPojo.class);
		query.setParameter("orderId", orderId);
		return query.getResultList();
	}
	public OrderItemPojo select(int id)
	{
		TypedQuery<OrderItemPojo> query = getQuery(select_Id,OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public void delete(int orderId)
	{
		Query query = em().createQuery(delete_OrderId);
		query.setParameter("orderId", orderId);
		query.executeUpdate();
	}
	public void deleteItem(int id)
	{
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	public void update(OrderItemPojo p){

	}
}
