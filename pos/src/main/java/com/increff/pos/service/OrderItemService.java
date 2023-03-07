package com.increff.pos.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;

import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;
	@Autowired
	private OrderService orderService;
	public void add(OrderItemPojo p) throws ApiException {
		orderService.reduceInventory(p.getQuantity(),p.getProductId());
		dao.insert(p);
	}
	
	public void delete(int orderId) throws ApiException {
		List<OrderItemPojo> orderItemPojos = dao.selectAll(orderId);
		for(int i=0;i<orderItemPojos.size();i++)
		{
			int quantity = -orderItemPojos.get(i).getQuantity();
			orderService.reduceInventory(quantity,orderItemPojos.get(i).getProductId());
		}
          dao.delete(orderId);
	}
	public void deleteItem(int id) throws ApiException {
		OrderItemPojo p = get(id);
		orderService.reduceInventory(-p.getQuantity(),p.getProductId());
		dao.deleteItem(id);
	}

	public List<OrderItemPojo> getAll(int orderId)
	{
		return dao.selectAll(orderId);
	}
	public OrderItemPojo get(int id)
	{
		return dao.select(id);
	}
	public void update(int id,OrderItemPojo pojo) throws ApiException {
		OrderItemPojo p=dao.select(id);
		orderService.reduceInventory(pojo.getQuantity()-p.getQuantity(),p.getProductId());
		p.setSellingPrice(pojo.getSellingPrice());
		p.setQuantity(pojo.getQuantity());
	}

}
