package com.increff.employee.dto;

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

    public List<OrderItemData> get(int OrderId) throws ApiException {
       List<OrderItemPojo> list1 = service.get(OrderId);
       List<OrderItemData> list2 = new ArrayList<>();
       for(OrderItemPojo pojo : list1)
       {
           list2.add(convertToOrderItemData(pojo));
       }
       return list2;
    }

    public OrderItemData convertToOrderItemData(OrderItemPojo p) throws ApiException {
        ProductData productData = productDto.get(p.getProductId());
        OrderItemData data = new OrderItemData();
        data.setId(p.getId());
        data.setName(productData.getName());
        data.setQuantity(p.getQuantity());
        data.setSellingPrice(p.getSellingPrice());
        data.setProductId(p.getProductId());
        return data;
    }
}
