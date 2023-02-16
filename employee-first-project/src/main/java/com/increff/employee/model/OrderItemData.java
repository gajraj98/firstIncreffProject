package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {

    private int id;
    private String name;
    private int quantity;
    private double sellingPrice;
    private  int productId;
}
