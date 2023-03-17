package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(
        name = "daily_report"
)
public class DailyReportPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private int totalInvoice;
    private int totalItems;
    private int totalRevenue;
}
