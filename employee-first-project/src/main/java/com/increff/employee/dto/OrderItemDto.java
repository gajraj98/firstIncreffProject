package com.increff.employee.dto;

import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.ConvertFunctions.convertToOrderItem;
import static com.increff.employee.util.ConvertFunctions.convertToOrderItemData;

@Repository
public class OrderItemDto {
    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDto orderDto;

    public void add(OrderForm f) throws ApiException {
        orderDto.isInvoiceGenerated(f.getOrderId());
        ProductPojo productPojo = productService.get(f.getBarcode());
        OrderItemPojo p = convertToOrderItem(f,productPojo.getId());
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
           ProductPojo productPojo = productService.get(pojo.getProductId());
           list2.add(convertToOrderItemData(pojo,productPojo.getBarcode(),productPojo.getName()));
       }
       return list2;
    }
    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo pojo = service.get(id);
        ProductPojo productPojo = productService.get(pojo.getProductId());
        return convertToOrderItemData(pojo,productPojo.getBarcode(),productPojo.getName());
    }
    public void delete(int id) throws ApiException {
        service.deleteItem(id);
    }
    public void update( int id,OrderForm form) throws ApiException {
        ProductPojo productPojo = productService.get(form.getBarcode());
        OrderItemPojo p = convertToOrderItem(form,productPojo.getId());
        service.update(id,p);
    }


    public void deleteItem( int id) throws ApiException {
        service.deleteItem(id);
    }
}
