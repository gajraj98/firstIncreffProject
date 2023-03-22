package com.increff.pos.dto;

import com.increff.pos.client.InvoiceClient;
import com.increff.pos.model.InvoiceDetails;
import com.increff.pos.model.InvoiceItems;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceDto {
    @Autowired
    private InvoiceClient invoiceClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;

    public void generateInvoice(String orderCode, HttpServletResponse response) throws ApiException, IOException {
//        orderDto.isInvoiceGenerated(Integer.valueOf(orderCode));
        OrderPojo orderPojo = orderService.get(Integer.valueOf(orderCode));
        if (orderPojo == null) {
            throw new ApiException("No Order with OrderId " + orderCode + "is exist");
        }
        getOrderItems(orderPojo.getId(), response);
    }

    public void getOrderItems(int orderId, HttpServletResponse response) throws ApiException, IOException {
        List<OrderItemPojo> orderItemDataList = orderItemService.getAll(orderId);
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();

        for (OrderItemPojo pojo : orderItemDataList) {
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
        invoiceClient.generateInvoice(invoiceDetails, response);
        orderService.markInvoiceGenerated(orderId);

    }

}
