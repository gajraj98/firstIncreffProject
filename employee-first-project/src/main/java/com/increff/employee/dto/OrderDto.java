package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.springframework.transaction.TransactionUsageException;

@Repository
public class OrderDto {

	@Autowired
	private OrderService service;
	@Autowired
	private ProductService productService;
	@Autowired
	private InventoryDto inventoryDto;
	@Autowired
	private OrderItemService orderItemService;

	public void add(List<OrderForm> form) throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setTime(java.time.LocalDateTime.now());
		HashMap<Integer,OrderItemPojo> orderItemPojoHashMap = new HashMap<Integer,OrderItemPojo>();
		List<OrderItemPojo> itemList = new ArrayList<OrderItemPojo>();
		for(OrderForm f : form) {
			OrderItemPojo pItem = convertToOrderItem(f);
			if(orderItemPojoHashMap.containsKey(pItem.getProductId())==false){
				orderItemPojoHashMap.put(pItem.getProductId(), pItem);
			}
			else{
                if(orderItemPojoHashMap.get(pItem.getProductId()).getSellingPrice()!= pItem.getSellingPrice())
				{
					throw new ApiException("Selling price of two same products can't be different");
				}
				int prv_quantity = orderItemPojoHashMap.get(pItem.getProductId()).getQuantity() + pItem.getQuantity();
				orderItemPojoHashMap.get(pItem.getProductId()).setQuantity(prv_quantity);
			}
		}
		for(Map.Entry<Integer,OrderItemPojo> entry :orderItemPojoHashMap.entrySet())
		{
			itemList.add(entry.getValue());
		}
		System.out.println("calling Order service");
		service.add(p,itemList);
	}
	public OrderPojo get(String time) {
		return service.get(time);
	}

	public OrderData get(int id) {
		return convertDataTOForm(service.get(id));
	}
    public void delete(int id) throws ApiException {
         service.delete(id);
	}
	public List<OrderData> getAll()
	{
		List<OrderPojo> list = service.getAll();
		List<OrderData> list2 = new ArrayList<OrderData>();
		for(OrderPojo l : list) {
			list2.add(convertDataTOForm(l));
		}
		return list2;
	}
	public void update(int id,List<OrderForm> form) throws ApiException {
		HashMap<Integer,OrderItemPojo> orderItemPojoHashMap = new HashMap<Integer,OrderItemPojo>();
		List<OrderItemPojo> orderItemPojos2 = new ArrayList<OrderItemPojo>();
		for(OrderForm f : form) {
			OrderItemPojo pItem = convertToOrderItem(f);
			if(orderItemPojoHashMap.containsKey(pItem.getProductId())==false){
				orderItemPojoHashMap.put(pItem.getProductId(), pItem);
			}
			else{
				if(orderItemPojoHashMap.get(pItem.getProductId()).getSellingPrice()!= pItem.getSellingPrice())
				{
					throw new ApiException("Selling price of two same products can't be different");
				}
				int prv_quantity = orderItemPojoHashMap.get(pItem.getProductId()).getQuantity() + pItem.getQuantity();
				orderItemPojoHashMap.get(pItem.getProductId()).setQuantity(prv_quantity);
			}
		}
		for(Map.Entry<Integer,OrderItemPojo> entry :orderItemPojoHashMap.entrySet())
		{
			orderItemPojos2.add(entry.getValue());
		}
		orderItemService.update(id,orderItemPojos2);
	}
	public OrderItemPojo convertToOrderItem(OrderForm f) throws ApiException
	{
		OrderItemPojo pItem = new OrderItemPojo();
		ProductPojo pojoProduct = productService.get(f.getBarcode());
		if(pojoProduct == null)
		{
			throw new ApiException("Product is not available in inventory");
		}
		pItem.setProductId(pojoProduct.getId());
		pItem.setSellingPrice(f.getMrp());
		pItem.setQuantity(f.getQuantity());
		return pItem;
	}
	public OrderData convertDataTOForm(OrderPojo p) {
		OrderData d = new OrderData();
		d.setId(p.getId());
		d.setTime(p.getTime());
		return d;
	}
    public InventoryForm convertDataTOForm(InventoryData data)
	{
		InventoryForm pojo = new InventoryForm();
		pojo.setId(data.getId());
		pojo.setInventory(data.getInventory());
		return pojo;
	}

}
