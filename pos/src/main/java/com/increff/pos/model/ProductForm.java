package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductForm {

    // todo use lombok or add a validaiton in dto
    private String name;
    private double mrp;
    private String barcode;
    private String brand;
    private String category;


}
