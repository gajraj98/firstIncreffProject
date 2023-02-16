package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(columnList = "id,inventory")},
name = "Inventory"
	)
@Setter
@Getter
public class InventoryPojo {

	@Id
	private int id;
	private int inventory;
}
