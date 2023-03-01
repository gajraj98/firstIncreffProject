package com.increff.employee.dto;

import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItemDto {
    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private OrderDto orderDto;

    public void add(OrderForm f) throws ApiException {
        orderDto.isInvoiceGenerated(f.getOrderId());
        OrderItemPojo p = convertToOrderItem(f);
        service.add(p);
    }
    public List<OrderItemData> getAllCheckInvoiceBefore(int OrderId) throws ApiException {
        orderDto.isInvoiceGenerated(OrderId);
        return getAll(OrderId);
    }
    public List<OrderItemData> getAll(int OrderId) throws ApiException {
       List<OrderItemPojo> list1 = service.getAll(OrderId);
       List<OrderItemData> list2 = new ArrayList<>();
       for(OrderItemPojo pojo : list1)
       {
           list2.add(convertToOrderItemData(pojo));
       }
       return list2;
    }
    public OrderItemData get(int id) throws ApiException {
        return convertToOrderItemData(service.get(id));
    }
    public void delete(int id) throws ApiException {
        service.deleteItem(id);
    }
    public void update( int id,OrderForm form) throws ApiException {
        OrderItemPojo p = convertToOrderItem(form);
        service.update(id,p);
    }
    public OrderItemData convertToOrderItemData(OrderItemPojo p) throws ApiException {
        ProductData productData = productDto.get(p.getProductId());
        OrderItemData data = new OrderItemData();
        data.setId(p.getId());
        data.setName(productData.getName());
        data.setQuantity(p.getQuantity());
        data.setSellingPrice(p.getSellingPrice());
        data.setProductId(p.getProductId());
        data.setBarcode(productData.getBarcode());
        return data;
    }
    public OrderItemPojo convertToOrderItem(OrderForm f) throws ApiException
    {
        OrderItemPojo pItem = new OrderItemPojo();
        ProductData dataProduct = productDto.get(f.getBarcode());
        pItem.setProductId(dataProduct.getId());
        pItem.setSellingPrice(f.getMrp());
        pItem.setQuantity(f.getQuantity());
        pItem.setOrderId(f.getOrderId());
        return pItem;
    }

    public void deleteItem( int id) throws ApiException {
        service.deleteItem(id);
    }
}
