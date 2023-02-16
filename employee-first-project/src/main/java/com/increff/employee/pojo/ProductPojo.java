package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "barcode,brandCategoryId,name,mrp")},
 name = "Products"
	)
@Getter
@Setter
public class ProductPojo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private int brandCategoryId;
	@Column(unique = true,nullable = false)
	private String barcode;
	private String name;
	private double mrp;
	
}
