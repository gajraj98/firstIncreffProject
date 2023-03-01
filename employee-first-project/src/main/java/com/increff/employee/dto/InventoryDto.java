package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.ProductData;
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
		InventoryPojo p = getCheck(id);
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
		ProductData dataProduct = productDto.get(barcode);
		InventoryPojo p = convert(f);
		service.update(dataProduct.getId(), p);
	}
	public void update(int id, InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		getCheck(id);
		service.update(id, p);
	}
	protected static InventoryPojo convert(InventoryForm f) {
		InventoryPojo p = new InventoryPojo();
		p.setId(f.getId());
		p.setInventory(f.getInventory());
		return p;
	}

	protected static InventoryData convert(InventoryPojo p,ProductData productData) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setBarcode(productData.getBarcode());
		d.setName(productData.getName());
		d.setInventory(p.getInventory());
		return d;
	}
	public InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo p = service.get(id);
		if(p == null) {
			throw new ApiException("Product doesn't exist");
		}
		return p;
	}

}

