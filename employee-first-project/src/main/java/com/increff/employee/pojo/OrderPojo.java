package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(columnList = "time")},
name = "Orders"
	)
public class OrderPojo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	 private int id;
	 private LocalDateTime time;
//	 private String status = "PENDING";
	 
}
