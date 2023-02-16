package com.increff.invoice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceDetails {

    private int orderId;
    private LocalDateTime time;
    private List<InvoiceItem> items;
}
