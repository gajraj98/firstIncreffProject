package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SalesReportForm {

    private String startDate;
    private String endDate;
    private String brand;
    private String category;
}
