package com.increff.employee.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.increff.employee.model.*;
import com.increff.employee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	private ProductDto productDto;
	@Autowired
	private InventoryDto inventoryDto;
	@Autowired
	private OrderItemService orderItemService;

//	converting OrderForm int orderItem and adding order
	public void add(List<OrderForm> form) throws ApiException {
		OrderPojo p = new OrderPojo();
		p.setTime(java.time.LocalDateTime.now());
		HashMap<Integer,OrderItemPojo> orderItemPojoHashMap = new HashMap<Integer,OrderItemPojo>();
		List<OrderItemPojo> itemList = new ArrayList<OrderItemPojo>();
		for(OrderForm f : form) {
			OrderItemPojo pItem = convertToOrderItem(f);

//			adding items in hashmap if there are more than one same item the adding their quantity after checking their price
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

//		adding all items into itemList
		for(Map.Entry<Integer,OrderItemPojo> entry :orderItemPojoHashMap.entrySet())
		{
			itemList.add(entry.getValue());
		}

//		calling orderService
		service.add(p,itemList);
	}
	public void markInvoiceGenerated(int orderId)
	{
          service.markInvoiceGenerated(orderId);
	}

	public OrderPojo get(String time) {
		return service.get(time);
	}

//	retrieving OrderPojo bt its id
	public OrderData get(int id) {
		return convertDataTOForm(service.get(id));
	}
//	get orders from date
	public List<OrderPojo> getByDate(LocalDateTime start , LocalDateTime end)
	{
		return service.getByDate(start,end);
	}
//	deleting order by its id
    public void delete(int id) throws ApiException {
		isInvoiceGenerated(id);
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

	public boolean isInvoiceGenerated(int orderId) throws ApiException {
        OrderPojo orderPojo=service.get(orderId);
		if(orderPojo.getInvoiceGenerated()>0)
		{
			throw new ApiException("Invoice is all ready generated");
		}
		return true;
	}
	public OrderItemPojo convertToOrderItem(OrderForm f) throws ApiException
	{
		OrderItemPojo pItem = new OrderItemPojo();
		ProductData dataProduct = productDto.get(f.getBarcode());
		if(dataProduct == null)
		{
			throw new ApiException("Product is not available in inventory");
		}
		pItem.setProductId(dataProduct.getId());
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
}
