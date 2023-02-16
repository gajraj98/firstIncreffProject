package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
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
	@Autowired
	private ProductDto productDto;
	public InventoryData get(int id) throws ApiException {
		InventoryPojo p = service.get(id);
		ProductData productData = productDto.get(p.getId());
		return convert(p,productData);
	}

	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> list = service.getAll();
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			ProductData productData = productDto.get(p.getId());
			list2.add(convert(p,productData));
		}
		return list2;
	}

	public void update(String barcode, InventoryForm f) throws ApiException {
		ProductPojo productPojo = productDto.get(barcode);
		InventoryPojo p = convert(f);
		service.update(productPojo.getId(), p);
	}
	public void update(int id, InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		service.update(id, p);
	}
	private static InventoryPojo convert(InventoryForm f) {
		InventoryPojo p = new InventoryPojo();
		p.setId(f.getId());
		p.setInventory(f.getInventory());
		return p;
	}

	private static InventoryData convert(InventoryPojo p,ProductData productData) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setBarcode(productData.getBarcode());
		d.setName(productData.getName());
		d.setInventory(p.getInventory());
		return d;
	}


}

