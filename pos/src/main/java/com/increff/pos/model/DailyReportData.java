package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyReportData {

    private String date;
    private int totalInvoice;
    private int totalItems;
    private int totalRevenue;
}
