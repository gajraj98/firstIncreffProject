package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.InventoryService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

@Repository
public class ProductDto {

	@Autowired
	private ProductService service;
	@Autowired
	private InventoryService inventoryService;

	public void add(ProductForm form) throws ApiException {
		ProductPojo p = convert(form);
		normalize(p);
		int id=service.add(p);
		InventoryPojo p2 = new InventoryPojo();
		p2.setId(id);
		p2.setBarcode(p.getBarcode());
		inventoryService.add(p2);
	}

	public void delete(int id) {
		service.delete(id);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo p = service.get(id);
		return convert(p);
	}

	public List<ProductData> getAll() {
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public void update(int id, ProductForm f) throws ApiException {
		ProductPojo p = convert(f);
		normalize(p);
		service.update(id, p);
	}

	private static ProductPojo convert(ProductForm f) {
		ProductPojo p = new ProductPojo();
		p.setMrp(f.getMrp());
		p.setName(f.getName());
		p.setBarcode(f.getBarcode());
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}

	private static ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setName(p.getName());
		d.setId(p.getId());
		d.setMrp(p.getMrp());
		d.setBrand(p.getBrand());
		d.setBarcode(p.getBarcode());
		d.setCategory(p.getCategory());
		System.out.println(d);
		return d;
	}
	protected static void normalize(ProductPojo p) {
		p.setName(StringUtil.toLowerCase(p.getName()));
		p.setBrand(StringUtil.toLowerCase(p.getBrand()));
		p.setCategory(StringUtil.toLowerCase(p.getCategory()));
	}

}
