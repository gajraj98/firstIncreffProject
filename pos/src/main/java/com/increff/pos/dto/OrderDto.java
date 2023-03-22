package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.util.ConvertFunctions.convertDataTOForm;
import static com.increff.pos.util.ConvertFunctions.convertToOrderItem1;
import static com.increff.pos.util.StringUtil.roundOff;

@Repository
public class OrderDto {

    @Autowired
    private OrderService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;

    // todo rename variables
    public static List<OrderData> createList(List<OrderPojo> list) {
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo l : list) {
            list2.add(convertDataTOForm(l));
        }
        return list2;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderForm> orderForms) throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setTime(java.time.LocalDateTime.now());
        HashMap<Integer, OrderItemPojo> orderItemPojoHashMap = new HashMap<Integer, OrderItemPojo>();
        List<OrderItemPojo> orderItemPojos = new ArrayList<OrderItemPojo>();
        for (OrderForm orderForm : orderForms) {
            ProductPojo pojoProduct = productService.get(orderForm.getBarcode());
            OrderItemPojo orderItemPojo = convertToOrderItem1(orderForm, pojoProduct.getId());
            orderForm.setMrp(roundOff(orderForm.getMrp()));

            if (!orderItemPojoHashMap.containsKey(orderItemPojo.getProductId())) {
                orderItemPojoHashMap.put(orderItemPojo.getProductId(), orderItemPojo);
            } else {
                if (orderItemPojoHashMap.get(orderItemPojo.getProductId()).getSellingPrice() != orderItemPojo.getSellingPrice()) {
                    throw new ApiException("Selling price of two same products can't be different");
                }
                int prv_quantity = orderItemPojoHashMap.get(orderItemPojo.getProductId()).getQuantity() + orderItemPojo.getQuantity();
                orderItemPojoHashMap.get(orderItemPojo.getProductId()).setQuantity(prv_quantity);
            }
        }

        for (Map.Entry<Integer, OrderItemPojo> entry : orderItemPojoHashMap.entrySet()) {
            orderItemPojos.add(entry.getValue());
        }


        service.add(orderPojo, orderItemPojos);
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemPojo.setOrderId(orderPojo.getId());
            inventoryService.reduceInventory(orderItemPojo.getQuantity(), orderItemPojo.getProductId());
            orderItemService.add(orderItemPojo);
        }
    }

    public void markInvoiceGenerated(int orderId) {
        service.markInvoiceGenerated(orderId);
    }

    public OrderData get(int id) {
        return convertDataTOForm(service.get(id));
    }

    public List<OrderPojo> getByDate(LocalDateTime start, LocalDateTime end) {
        return service.getByDate(start, end);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {
        checkInvoiceGenerated(id);
        service.delete(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.getAll(id);
        for (int i = 0; i < orderItemPojos.size(); i++) {
            int quantity = orderItemPojos.get(i).getQuantity();
            inventoryService.addBackInventory(quantity, orderItemPojos.get(i).getProductId());
        }
        orderItemService.deleteItem(id);
    }

    public Long getTotalNoOrders() {

        return service.getTotalNoOrders();
    }

    public List<OrderData> getLimited(Integer pageNo) {
        return createList(service.getLimited(pageNo));
    }

    public List<OrderData> getAll() {
        return createList(service.getAll());

    }

    public boolean checkInvoiceGenerated(int orderId) throws ApiException {
        OrderPojo orderPojo = service.get(orderId);
        if (orderPojo.getInvoiceGenerated() > 0) {
            throw new ApiException("Invoice is all ready generated");
        }
        return true;
    }
}
