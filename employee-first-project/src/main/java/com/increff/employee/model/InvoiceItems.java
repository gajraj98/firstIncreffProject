package com.increff.employee.model;

import lombok.Data;

@Data
public class InvoiceItems {
    private double price;
    private Integer quantity;
    private String name;
}
