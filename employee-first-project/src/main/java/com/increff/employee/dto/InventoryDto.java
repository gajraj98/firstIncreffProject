package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;

@Repository
public class InventoryDto {

	@Autowired
	private InventoryService service;
	public InventoryData get(int id) throws ApiException {
		InventoryPojo p = service.get(id);
		return convert(p);
	}

	public List<InventoryData> getAll() {
		List<InventoryPojo> list = service.getAll();
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public void update(String barcode, InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		service.update(barcode, p);
	}
	public void update(int id, InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		service.update(id, p);
	}
	private static InventoryPojo convert(InventoryForm f) {
		InventoryPojo p = new InventoryPojo();
		p.setId(f.getId());
		p.setBarcode(f.getBarcode());
		p.setInventory(f.getInventory());
		return p;
	}

	private static InventoryData convert(InventoryPojo p) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setBarcode(p.getBarcode());
		d.setInventory(p.getInventory());
		return d;
	}
}

