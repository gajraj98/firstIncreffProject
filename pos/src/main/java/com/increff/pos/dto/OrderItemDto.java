package com.increff.pos.dto;

import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convertToOrderItem;
import static com.increff.pos.util.ConvertFunctions.convertToOrderItemData;

@Repository
@Transactional(rollbackOn = ApiException.class)
public class OrderItemDto {
    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;
    public void add(OrderForm orderForm) throws ApiException {
        orderDto.checkInvoiceGenerated(orderForm.getOrderId());
        ProductPojo productPojo = productService.get(orderForm.getBarcode());
        if(productPojo.getMrp()<orderForm.getMrp())
        {
            throw new ApiException("selling price can't be greater then mrp");
        }
        OrderItemPojo orderItemPojo = convertToOrderItem(orderForm,productPojo.getId());
        service.add(orderItemPojo);
        inventoryService.reduceInventory(orderItemPojo.getQuantity(), orderItemPojo.getProductId());
        orderService.update(orderItemPojo.getOrderId());
    }

    public List<OrderItemData> getAll(int OrderId) throws ApiException {
       List<OrderItemPojo> orderItemPojoList = service.getAll(OrderId);
       List<OrderItemData> orderItemDataList = new ArrayList<>();
       for(OrderItemPojo pojo : orderItemPojoList)
       {
           ProductPojo productPojo = productService.get(pojo.getProductId());
           orderItemDataList.add(convertToOrderItemData(pojo,productPojo.getBarcode(),productPojo.getName()));
       }
       return orderItemDataList;
    }
    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo pojo = service.get(id);
        ProductPojo productPojo = productService.get(pojo.getProductId());
        return convertToOrderItemData(pojo,productPojo.getBarcode(),productPojo.getName());
    }
    public void delete(int orderId) throws ApiException {
        OrderItemPojo orderItemPojo = service.get(orderId);
        orderDto.checkInvoiceGenerated(orderItemPojo.getOrderId());
        List<OrderItemPojo> orderItemPojos = service.getAll(orderId);
        for (int i = 0; i < orderItemPojos.size(); i++) {
            int quantity = orderItemPojos.get(i).getQuantity();
            inventoryService.addBackInventory(quantity, orderItemPojos.get(i).getProductId());
        }
        service.deleteItem(orderId);
    }

    public void update( int id,OrderForm form) throws ApiException {
        orderDto.checkInvoiceGenerated(form.getOrderId());
        ProductPojo productPojo = productService.get(form.getBarcode());
        OrderItemPojo orderItemPojo = convertToOrderItem(form,productPojo.getId());
        service.update(id,orderItemPojo);
        OrderItemPojo pojo = service.get(id);
        inventoryService.reduceInventory(orderItemPojo.getQuantity() - pojo.getQuantity(), pojo.getProductId());
        orderService.update(orderItemPojo.getOrderId());
    }


    public void deleteItem( int id) throws ApiException {
        OrderItemPojo p = service.get(id);
        inventoryService.addBackInventory(p.getQuantity(), p.getProductId());
        service.deleteItem(id);
    }
}
