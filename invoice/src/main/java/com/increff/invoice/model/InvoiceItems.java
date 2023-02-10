package com.increff.invoice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class InvoiceItems {
    private double price;
    private Integer quantity;
    private String name;
}
