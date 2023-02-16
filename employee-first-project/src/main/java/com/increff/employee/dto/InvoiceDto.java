package com.increff.employee.dto;

import com.increff.employee.client.InvoiceClient;
import com.increff.employee.model.InvoiceDetails;
import com.increff.employee.model.InvoiceItems;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceClient invoiceClient;
    public String generateInvoice(String orderCode) throws ApiException {
        System.out.println("invoiceDto");
        OrderPojo orderPojo = orderService.get(Integer.valueOf(orderCode));
        if(orderPojo==null)
        {
            throw new ApiException("No Order with OrderId " + orderCode +"is exist");
        }
        return getOrderItems(orderPojo.getId());
    }
    public String getOrderItems(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.get(orderId);
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();

        for(OrderItemPojo pojo:orderItemPojoList){
            InvoiceItems invoiceItems = new InvoiceItems();
            ProductPojo productPojo = productService.get(pojo.getProductId());
            invoiceItems.setName(productPojo.getName());
            invoiceItems.setQuantity(pojo.getQuantity());
            invoiceItems.setPrice(pojo.getSellingPrice());
            invoiceItemsList.add(invoiceItems);
        }
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        LocalDateTime time = orderService.get(orderId).getTime();
        invoiceDetails.setTime(time);
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItems(invoiceItemsList);
        return invoiceClient.generateInvoice(invoiceDetails);
    }

}
