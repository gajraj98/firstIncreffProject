package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "Inventory"
)
@Setter
@Getter
public class InventoryPojo {

    @Id
    private int id;
    private int inventory;
}
