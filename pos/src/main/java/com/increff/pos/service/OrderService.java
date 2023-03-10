package com.increff.pos.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired InventoryService inventoryService;

	public void add(OrderPojo orderpojo,List<OrderItemPojo> orderItemPojos) throws ApiException {
		dao.insert(orderpojo);
		for(OrderItemPojo orderItemPojo : orderItemPojos) {
			orderItemPojo.setOrderId(orderpojo.getId());
			reduceInventory(orderItemPojo.getQuantity(),orderItemPojo.getProductId());
			orderItemService.add(orderItemPojo);
		}
	}
	public void markInvoiceGenerated(int orderId)
	{
          OrderPojo p=get(orderId);
		  p.setInvoiceGenerated(1);
		  return;
	}
	public void delete(int id) throws ApiException {
		orderItemService.delete(id);
		dao.delete(id);
	}
	public Long getTotalNoOrders() {

		return dao.getTotalNoOrders();
	}
	public OrderPojo get(String time)
	{
		return dao.select(time);
	}
	public OrderPojo get(int id)
	{
		return dao.select(id);
	}
	public List<OrderPojo> getByDate(LocalDateTime start , LocalDateTime end)
	{
		return dao.selectByDate(start,end);
	}
	public List<OrderPojo> getAll()
	{
		return dao.selectAll();
	}
	public List<OrderPojo> getLimited(Integer pageNo)
	{
		return dao.selectlimited(pageNo);
	}

	public void reduceInventory(int quantity,int productId) throws ApiException {
		InventoryPojo inventoryPojo = inventoryService.get(productId);
		int currentInventory = inventoryPojo.getInventory();
		if(currentInventory<quantity)
		{
			throw new ApiException("MAX " + currentInventory + " inventory is available you exceed the limit ");
		}
		currentInventory = currentInventory - quantity;
		inventoryPojo.setInventory(currentInventory);
		inventoryService.update(productId,inventoryPojo);
	}
	
}
