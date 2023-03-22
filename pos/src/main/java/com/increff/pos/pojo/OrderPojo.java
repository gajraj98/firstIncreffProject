package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "Orders"
)
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime time;
    private LocalDateTime lastUpdate;
    private int invoiceGenerated;
    private String invoiceString;
//	 private String status = "PENDING";

}
