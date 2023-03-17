package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportData {
    private String brand;
    private String category;
    private int inventory;
    private String name;
    private String barcode;
}
