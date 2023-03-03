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

import static com.increff.employee.util.ConvertFunctions.convertDataTOForm;
import static com.increff.employee.util.ConvertFunctions.convertToOrderItem1;

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
			ProductPojo pojoProduct = productService.get(f.getBarcode());
			OrderItemPojo pItem = convertToOrderItem1(f,pojoProduct.getId());

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


		service.add(p,itemList);
	}
	public void markInvoiceGenerated(int orderId)
	{
          service.markInvoiceGenerated(orderId);
	}

	public OrderPojo get(String time) {
		return service.get(time);
	}


	public OrderData get(int id) {
		return convertDataTOForm(service.get(id));
	}

	public List<OrderPojo> getByDate(LocalDateTime start , LocalDateTime end)
	{
		return service.getByDate(start,end);
	}

    public void delete(int id) throws ApiException {
		isInvoiceGenerated(id);
         service.delete(id);
	}
	public List<OrderData> getAll()
	{
		return conversion(service.getAll());

	}

	public boolean isInvoiceGenerated(int orderId) throws ApiException {
        OrderPojo orderPojo=service.get(orderId);
		if(orderPojo.getInvoiceGenerated()>0)
		{
			throw new ApiException("Invoice is all ready generated");
		}
		return true;
	}
    public static List<OrderData> conversion(List<OrderPojo> list){
		List<OrderData> list2 = new ArrayList<OrderData>();
		for(OrderPojo l : list) {
			list2.add(convertDataTOForm(l));
		}
		return list2;
	}
}
