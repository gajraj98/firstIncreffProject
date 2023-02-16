package com.increff.employee.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired InventoryService inventoryService;

	@Transactional
	public void add(OrderPojo orderpojo,List<OrderItemPojo> orderItemPojos) throws ApiException {
		dao.insert(orderpojo);
		for(OrderItemPojo orderItemPojo : orderItemPojos) {
			orderItemPojo.setOrderId(orderpojo.getId());
			reduceInventory(orderItemPojo.getQuantity(),orderItemPojo.getProductId());
			orderItemService.add(orderItemPojo);
		}
	}
	@Transactional
	public void delete(int id) throws ApiException {
		dao.delete(id);
		orderItemService.delete(id);
	}
	@Transactional
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
	@Transactional
	public List<OrderPojo> getAll()
	{
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
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
