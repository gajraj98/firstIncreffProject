package com.increff.employee.service;

import javax.transaction.Transactional;

import com.increff.employee.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;
	@Autowired
	private OrderService orderService;
	public void add(OrderItemPojo p)
	{
		System.out.print("calling OrderItem Dao");
		dao.insert(p);
	}
	public void delete(int orderId) throws ApiException {
		List<OrderItemPojo> orderItemPojos = dao.select(orderId);
		for(int i=0;i<orderItemPojos.size();i++)
		{
			int quantity = -orderItemPojos.get(i).getQuantity();
			orderService.reduceInventory(quantity,orderItemPojos.get(i).getProductId());
		}
          dao.delete(orderId);
	}
	public List<OrderItemPojo> get(int orderId)
	{
		return dao.select(orderId);
	}
	public void update(int id,List<OrderItemPojo>orderItemPojos2) throws ApiException {
		List<OrderItemPojo> orderItemPojos = dao.select(id);
		for(int i=0;i<orderItemPojos.size();i++){
			int productId = orderItemPojos.get(i).getProductId();
			int quantity = - orderItemPojos.get(i).getQuantity();
			orderItemPojos.get(i).setQuantity(0);
			orderService.reduceInventory(quantity,productId);
		}
		HashMap<Integer,OrderItemPojo>orderItemPojoHashMap = new HashMap<Integer, OrderItemPojo>();
		for(OrderItemPojo pojo :orderItemPojos)
		{
			orderItemPojoHashMap.put(pojo.getProductId(),pojo);
		}
		for(int i=0;i<orderItemPojos2.size();i++)
		{
			int productId = orderItemPojos2.get(i).getProductId();
			int quantity = orderItemPojos2.get(i).getQuantity();
			if(orderItemPojoHashMap.containsKey(productId)){
				orderService.reduceInventory(quantity,productId);
				orderItemPojoHashMap.get(productId).setQuantity(quantity);
				orderItemPojoHashMap.get(productId).setSellingPrice(orderItemPojos2.get(i).getSellingPrice());
			}
			else{
				OrderItemPojo p = orderItemPojos2.get(i);
				orderService.reduceInventory(p.getQuantity(),p.getProductId());
				add(p);
			}
		}
	}
}
