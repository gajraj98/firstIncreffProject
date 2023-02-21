package com.increff.employee.dto;

import com.increff.employee.client.InvoiceClient;
import com.increff.employee.model.*;
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
    private InvoiceClient invoiceClient;
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private OrderItemDto orderItemDto;
    public String generateInvoice(String orderCode) throws ApiException {
        orderDto.isInvoiceGenerated(Integer.valueOf(orderCode));
        OrderData orderData = orderDto.get(Integer.valueOf(orderCode));
        if(orderData==null)
        {
            throw new ApiException("No Order with OrderId " + orderCode +"is exist");
        }
        return getOrderItems(orderData.getId());
    }
    public String getOrderItems(int orderId) throws ApiException {
        List<OrderItemData> orderItemDataList = orderItemDto.getAll(orderId);
        List<InvoiceItems> invoiceItemsList = new ArrayList<>();

        for(OrderItemData pojo:orderItemDataList){
            InvoiceItems invoiceItems = new InvoiceItems();
            ProductData productData = productDto.get(pojo.getProductId());
            invoiceItems.setName(productData.getName());
            invoiceItems.setQuantity(pojo.getQuantity());
            invoiceItems.setPrice(pojo.getSellingPrice());
            invoiceItemsList.add(invoiceItems);
        }
        InvoiceDetails invoiceDetails = new InvoiceDetails();
        LocalDateTime time = orderDto.get(orderId).getTime();
        invoiceDetails.setTime(time);
        invoiceDetails.setOrderId(orderId);
        invoiceDetails.setItems(invoiceItemsList);
        String PdfString=invoiceClient.generateInvoice(invoiceDetails);
        orderDto.markInvoiceGenerated(orderId);
        return PdfString;
    }
}
